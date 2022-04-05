package it.dcm.storage.service.impl;


import com.google.firebase.auth.*;
import it.dcm.storage.configuration.GoogleCloudStorage;
import it.dcm.storage.service.FirebaseAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class FirebaseAuthenticationImpl implements FirebaseAuthentication {
    @Value("${url.flico.basePath}")
    private String pathFlico;
    @Autowired
    private GoogleCloudStorage gcs;

    @Override
    public FirebaseToken validateTokenId(String bearerToken) {
        try {
            FirebaseToken token = gcs.getAuth().verifyIdToken(bearerToken);
            log.info("User logged is : {} email and uid {}", token.getEmail(), token.getUid());
            return token;
        } catch (FirebaseAuthException e) {
            log.error("Error firebase {}", e.getAuthErrorCode());
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
            return gcs.getAuth().createUser(request);
        } catch (FirebaseAuthException authException){
            log.error("Error with creation user : {}",authException.getAuthErrorCode().toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, authException.getAuthErrorCode().toString());
        }
    }

    @Override
    public UserRecord getFromMail(String email) {
        try{
            return gcs.getAuth().getUserByEmail(email);
        } catch (FirebaseAuthException e) {
            if (e.getAuthErrorCode() == AuthErrorCode.USER_NOT_FOUND){
                log.info("user not found with this mail : {}", email);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            log.error("Other error {}", e.getAuthErrorCode().toString());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
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

}
