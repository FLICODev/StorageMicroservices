package it.dcm.storage.controller;


import it.dcm.rest.authentication.FirebaseAccount;
import it.dcm.storage.command.CreationUserCommand;
import it.dcm.storage.mapper.AccountMapper;
import it.dcm.storage.service.FirebaseAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/auth")
public class AuthenticationController {

    @Autowired
    private FirebaseAuthentication firebaseAuthentication;
    @Autowired
    private CreationUserCommand creationUserCommand;



    @GetMapping(value = "/validate-token")
    public ResponseEntity<Void> validateToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bearer Token is missing");

        this.firebaseAuthentication.validateTokenId(bearerToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/create")
    public ResponseEntity<FirebaseAccount> create(@RequestBody FirebaseAccount account){
        return new ResponseEntity<>(this.creationUserCommand.execute(account), HttpStatus.OK);
    }


    @GetMapping(value = "/find-email/{email}")
    public ResponseEntity<FirebaseAccount> getFromEmail(@PathVariable String email){
        this.firebaseAuthentication.getFromMail(email);
        return ResponseEntity.ok(
                AccountMapper.toAccount(
                        this.firebaseAuthentication.getFromMail(email)
                ));
    }



}
