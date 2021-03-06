package it.dcm.storage.service.impl;

import com.google.firebase.auth.*;
import it.dcm.rest.exception.ResponseEntityException;
import it.dcm.storage.configuration.GoogleCloudStorage;
import it.dcm.storage.exception.ExceptionEnum;
import it.dcm.storage.service.FirebaseAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static it.dcm.storage.exception.ExceptionEnum.*;

@Slf4j
@Service
public class FirebaseAuthenticationImpl implements FirebaseAuthentication {
    @Value("${url.flico.basePath}")
    private String pathFlico;
    @Autowired
    private GoogleCloudStorage gcs;



    @Override
    public UserRecord editDisplayName(String displayName, String uid){
        log.info("Edit displayName : {} {}", displayName, uid);
        try {
            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                    .setDisplayName(displayName);

            UserRecord record =  gcs.getAuth().updateUser(request);
            log.info("New Display name : {}", record.getDisplayName());
            return record;
        } catch (FirebaseAuthException authException){
            log.info("ERROR EXCEPTION {}", authException.getLocalizedMessage());
            if (authException.getAuthErrorCode() != null){
                log.info("Error with creation user : {}",authException.getAuthErrorCode().toString());
                throw new ResponseEntityException(AUTH_FIREBASE_ERROR, authException.getAuthErrorCode().toString(), HttpStatus.BAD_REQUEST);
            }
            log.info("Error exception non null {}", authException.getLocalizedMessage());
            throw new ResponseEntityException(GENERIC_ERROR, GENERIC_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(Exception ex){
            log.info("Error exception : {}", ex.getLocalizedMessage());
            throw new ResponseEntityException(ExceptionEnum.AUTH_USER_ALREADY_EXIST, "Error", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public FirebaseToken validateTokenId(String bearerToken) {
        try {
            log.info("First");
            log.info(bearerToken);
            FirebaseToken token = gcs.getAuth().verifyIdToken(bearerToken.substring(7));
            log.info("User logged is : {} email and uid {}", token.getEmail(), token.getUid());
            return token;
        } catch (FirebaseAuthException e) {
            log.info("Error firebase {}", e.getAuthErrorCode());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public UserRecord create(String email, String password, String label) {
        try{
            UserRecord.CreateRequest request = new UserRecord.CreateRequest();
            request.setEmail(email);
            request.setPassword(password);
            request.setDisplayName(label);
            request.setEmailVerified(false);
            request.setDisabled(false);
            return gcs.getAuth().createUser(request);
        } catch (FirebaseAuthException authException){
            if (authException.getAuthErrorCode() != null){
                log.error("Error with creation user : {}",authException.getAuthErrorCode().toString());
                throw new ResponseEntityException(AUTH_FIREBASE_ERROR, authException.getAuthErrorCode().toString(), HttpStatus.BAD_REQUEST);
            }
            log.error("Error exception non null {}", authException.getLocalizedMessage());
            throw new ResponseEntityException(GENERIC_ERROR, GENERIC_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(Exception ex){
            log.error("Error exception : {}", ex.getLocalizedMessage());
            throw new ResponseEntityException(ExceptionEnum.AUTH_USER_ALREADY_EXIST, "Error", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UserRecord getFromMail(String email) {
        try{
            return gcs.getAuth().getUserByEmail(email);
        } catch (FirebaseAuthException e) {
            if (e.getAuthErrorCode() == AuthErrorCode.USER_NOT_FOUND){
                log.info("user not found with this mail : {}", email);
                throw new ResponseEntityException(AUTH_USER_NOT_EXIST, AUTH_USER_NOT_EXIST.getMessage(), HttpStatus.NOT_FOUND);
            }
            log.error("Other error {}", e.getAuthErrorCode().toString());
            throw new ResponseEntityException(GENERIC_ERROR, GENERIC_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getLinkConfirmEmail(String email, String uid) {
        try {
            ActionCodeSettings settings = ActionCodeSettings.builder()
                    .setUrl(pathFlico + "/verified&uid="+uid)
                    .build();
            return this.gcs.getAuth().generateEmailVerificationLink(email, settings);
        } catch (FirebaseAuthException ex){
            log.error("Error with generation link email verification : {}", ex.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found or other error");
        }
    }

    @Override
    public String getLinkResetPassword(String email, String uid){
        try {
            ActionCodeSettings settings = ActionCodeSettings.builder()
                    .setUrl(pathFlico + "/verified&uid="+uid)
                    .build();
            return this.gcs.getAuth().generatePasswordResetLink(email, settings);
        } catch (FirebaseAuthException ex){
            log.error("Error with generation link email verification : {}", ex.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found or other error");
        }
    }

}
