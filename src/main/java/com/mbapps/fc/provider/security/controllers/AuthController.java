package com.mbapps.fc.provider.security.controllers;

import com.mbapps.fc.provider.security.jwt.JwtUtils;
import com.mbapps.fc.provider.security.services.AuthService;
import com.mbapps.fc.provider.security.payload.request.LoginRequest;
import com.mbapps.fc.provider.security.payload.request.SignupRequest;
import com.mbapps.fc.provider.security.payload.response.MessageResponse;
import com.mbapps.fc.provider.services.user.domain.payload.response.UserInfoResponse;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<UserInfoResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        LOGGER.info("Received Authenticate User request");
        UserInfoResponse userInfo = authService.authenticateUserAndGenerateCookie(loginRequest);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, userInfo.cookie().toString())
                .body(userInfo);
    }

    @PostMapping("/signout")
    public ResponseEntity<MessageResponse> logoutUser() {
        LOGGER.info("Received User sign out request");

        ResponseCookie clearedCookie = jwtUtils.getCleanJwtCookie();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, clearedCookie.toString());


        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(new MessageResponse("User logged out successfully!"));
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
