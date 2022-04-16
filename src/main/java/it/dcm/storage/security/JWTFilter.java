package it.dcm.storage.security;

import com.google.firebase.auth.FirebaseToken;

import it.dcm.rest.exception.ResponseEntityException;
import it.dcm.storage.dto.Credentials;
import it.dcm.storage.dto.User;
import it.dcm.storage.service.FirebaseAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static it.dcm.storage.exception.ExceptionEnum.AUTH_VALIDATION_TOKEN_NULL;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private FirebaseAuthentication firebaseAuthentication;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        authorize(request, request.getHeader("Authorization"));
        filterChain.doFilter(request, response);
    }


    /**
     * Logica per poter capire se autorizzare o no il token passato nell'header
     * @param request modello proveniente dalla richiesta
     * @param token Stringa proveniente dalla header Authorization
     */
    private void authorize(HttpServletRequest request, String token) {
        FirebaseToken account = null;

        //convalidate token
        if (validateToken(token)) {

            //validate token from firebase auth call the microservice
            log.info("Token is valid {}", token);
            account = firebaseAuthentication.validateTokenId(token);
            if (account == null){
                log.error("Authentication microservices is response error");
                throw new ResponseEntityException(
                        AUTH_VALIDATION_TOKEN_NULL,
                        AUTH_VALIDATION_TOKEN_NULL.getMessage(),
                        HttpStatus.FORBIDDEN
                );
            }

        }

        //Retrieve profile from DB if account not null
        User profile = firebaseTokenToUserDto(account);

        // if account and profile not null
        if (account != null && profile != null) {
            log.info("Account from firebase and profile is not null");
            // Create authentication token
            setUpAuthenticationSpring(profile, account);
            log.info("Security context is settings");
        }
    }


    private User firebaseTokenToUserDto(FirebaseToken decodedToken) {
        User user = null;
        if (decodedToken != null) {
            user = new User();
            user.setUid(decodedToken.getUid());
            user.setLabel(decodedToken.getName());
            user.setEmail(decodedToken.getEmail());
            user.setEmailVerified(decodedToken.isEmailVerified());
        }
        return user;
    }

    private void setUpAuthenticationSpring(User profile, FirebaseToken token){
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        profile,
                        new Credentials(token, profile.getUid(), new Date())
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Validation Token dalla header della request
     * @param token is a String della header
     * @return true/false
     */
    private boolean validateToken(String token){
        return token != null && !token.equals("null") &&
                !token.equalsIgnoreCase("undefined") &&
                token.contains("Bearer") && !token.contains("null");
    }

}
