package it.dcm.storage.security;

import com.google.firebase.auth.FirebaseToken;
import it.dcm.storage.service.FirebaseAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private FirebaseAuthentication firebaseAuthentication;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) {
            log.error("Ops the token is not present");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        log.info(bearerToken.substring(7));


        FirebaseToken toke = this.firebaseAuthentication.validateTokenId(bearerToken.substring(7));
        // retrieve user from DB


        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
        return "/api/auth/create".equals(path);
    }

}
