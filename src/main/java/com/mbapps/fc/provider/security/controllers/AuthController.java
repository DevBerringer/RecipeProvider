package com.mbapps.fc.provider.security.controllers;

import com.mbapps.fc.provider.security.jwt.JwtUtils;
import com.mbapps.fc.provider.security.services.AuthService;
import com.mbapps.fc.provider.security.payload.request.LoginRequest;
import com.mbapps.fc.provider.security.payload.request.RefreshTokenRequest;
import com.mbapps.fc.provider.security.payload.request.SignupRequest;
import com.mbapps.fc.provider.security.payload.response.LoginResponse;
import com.mbapps.fc.provider.security.payload.response.MessageResponse;
import com.mbapps.fc.provider.security.payload.response.TokenResponse;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        LOGGER.info("Received Authenticate User request for user: {}", loginRequest.getUsername());
        try {
            LoginResponse loginResponse = authService.authenticateUser(loginRequest);
            LOGGER.info("Authentication successful for user: {}", loginRequest.getUsername());
            return ResponseEntity.ok(loginResponse);
        } catch (AuthenticationException e) {
            LOGGER.error("Authentication failed for user: {} - {}", loginRequest.getUsername(), e.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Bad credentials"));
        } catch (Exception e) {
            LOGGER.error("Unexpected authentication error for user: {} - {}: {}", 
                    loginRequest.getUsername(), e.getClass().getSimpleName(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Bad credentials"));
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<MessageResponse> logoutUser(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        LOGGER.info("Received User sign out request");

        // Optionally validate the token if present (for logging or token invalidation)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtils.validateJwtToken(token)) {
                String username = jwtUtils.getUserNameFromJwtToken(token);
                LOGGER.info("User {} logged out", username);
                // Here you could blacklist the token if you implement token blacklisting
            }
        }

        // Return success regardless of token validity (for security, don't reveal if token was invalid)
        return ResponseEntity.ok()
                .body(new MessageResponse("User logged out successfully!"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        LOGGER.info("Received refresh token request");
        try {
            TokenResponse tokenResponse = authService.refreshToken(refreshTokenRequest);
            return ResponseEntity.ok(tokenResponse);
        } catch (RuntimeException e) {
            LOGGER.error("Token refresh failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid or expired refresh token"));
        } catch (Exception e) {
            LOGGER.error("Token refresh error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid or expired refresh token"));
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (signUpRequest.getUsername().contains("_TEST")) {
            MessageResponse response = authService.registerUser(signUpRequest);
            if (response.getMessage().startsWith("Error:")) {
                return ResponseEntity.badRequest().body(response);
            }
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }
}
