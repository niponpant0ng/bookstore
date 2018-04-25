package com.service.bookstore.config.security;

import com.service.bookstore.models.User;
import com.service.bookstore.services.JwtService;
import com.service.bookstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.login.CredentialException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by nipon on 4/24/18.
 */
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private String AUTHORIZATION_PROPERTY = "Authorization";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            UUID userId = jwtService.getUserIdFromToken(jwt);

            User user = userService.getUserById(userId);
        } catch (CredentialException crEx) {

        } catch (Exception ex) {

        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) throws CredentialException {
        String bearerToken = request.getHeader(AUTHORIZATION_PROPERTY);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }

        throw new CredentialException();
    }
}
