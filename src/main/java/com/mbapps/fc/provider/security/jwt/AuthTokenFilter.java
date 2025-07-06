package com.mbapps.fc.provider.security.jwt;

import java.io.IOException;

import com.mbapps.fc.provider.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private  JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = jwtUtils.getJwtFromCookies(request);

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (UsernameNotFoundException e) { // Catch specific user not found
            logger.error("User not found during authentication: {}", e.getMessage());
        } catch (JwtException | IllegalArgumentException e) { // Catch JWT related exceptions
            // JwtException covers MalformedJwtException, ExpiredJwtException, UnsupportedJwtException
            // IllegalArgumentException covers "JWT claims string is empty"
            logger.error("JWT validation failed: {}", e.getMessage());
        } catch (Exception e) { // Catch any other unexpected exceptions
            logger.error("An unexpected error occurred during authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}