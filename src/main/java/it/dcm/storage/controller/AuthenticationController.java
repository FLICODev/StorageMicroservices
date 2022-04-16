package it.dcm.storage.controller;


import com.google.firebase.auth.FirebaseToken;
import it.dcm.rest.authentication.FirebaseAccount;
import it.dcm.rest.exception.ResponseEntityException;
import it.dcm.storage.command.CreationUserCommand;
import it.dcm.storage.command.ResetPasswordCommand;
import it.dcm.storage.exception.ExceptionEnum;
import it.dcm.storage.mapper.AccountMapper;
import it.dcm.storage.service.FirebaseAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

import static it.dcm.storage.exception.ExceptionEnum.GENERIC_ERROR;

@Slf4j
@RestController
@RequestMapping(value = "api/auth")
public class AuthenticationController {

    @Autowired
    private FirebaseAuthentication firebaseAuthentication;
    @Autowired
    private CreationUserCommand creationUserCommand;
    @Autowired
    private ResetPasswordCommand resetPasswordCommand;



    @GetMapping(value = "/validate-token")
    public ResponseEntity<FirebaseAccount> validateToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bearer Token is missing");
        log.info("Token into controller not null");
        FirebaseToken token = this.firebaseAuthentication.validateTokenId(bearerToken);
        if (token == null)
            throw new ResponseEntityException(
                    GENERIC_ERROR,
                    GENERIC_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );

        FirebaseAccount firebaseAccount  = new FirebaseAccount();
        firebaseAccount.setUid(token.getUid());
        firebaseAccount.setEmail(token.getEmail());
        firebaseAccount.setEmailVerified(token.isEmailVerified());

        return new ResponseEntity<>(firebaseAccount, HttpStatus.OK);
    }


    @PostMapping(value = "/create")
    public ResponseEntity<FirebaseAccount> create(@RequestBody FirebaseAccount account){
        return new ResponseEntity<>(this.creationUserCommand.execute(account), HttpStatus.OK);
    }


    @GetMapping(value = "/find-email/{email}")
    public ResponseEntity<FirebaseAccount> getFromEmail(@PathVariable String email){
        return ResponseEntity.ok(
                AccountMapper.toAccount(
                        this.firebaseAuthentication.getFromMail(email)
                ));
    }


    @GetMapping(value = "/reset-password/{email}")
    public ResponseEntity<FirebaseAccount> getResetPassword(@PathVariable String email){
        resetPasswordCommand.execute(email);
        FirebaseAccount firebaseAccount = new FirebaseAccount();
        firebaseAccount.setEmail(email);
        return ResponseEntity.ok(firebaseAccount);
    }



}
