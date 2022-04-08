package it.dcm.storage.security;

import com.google.firebase.auth.FirebaseToken;
import it.dcm.storage.dto.Credentials;
import it.dcm.storage.dto.User;
import it.dcm.storage.service.FirebaseAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private FirebaseAuthentication firebaseAuthentication;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        authorize(request, (request.getHeader("Authorization") == null) ? null : request.getHeader("Authorization"));
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
        return "/api/auth/create".equals(path);
    }

    private void authorize(HttpServletRequest request, String token) {
        String sessionCookieValue = null;
        FirebaseToken decodedToken = null;
        Credentials.CredentialType type = null;


        if ( token != null && !token.equals("null")
                && !token.equalsIgnoreCase("undefined")) {
            decodedToken = firebaseAuthentication.validateTokenId(token);
            type = Credentials.CredentialType.ID_TOKEN;
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        User user = firebaseTokenToUserDto(decodedToken);
        // Handle roles
        if (user != null) {
            // Set security context
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                    new Credentials(type, decodedToken, token, sessionCookieValue), authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
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

}
