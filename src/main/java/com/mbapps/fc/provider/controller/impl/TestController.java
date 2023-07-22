package com.mbapps.fc.provider.controller.impl;

import com.mbapps.fc.provider.payload.response.RecipeResponseDTO;
import com.mbapps.fc.provider.payload.response.AuthResponse;
import com.mbapps.fc.provider.payload.response.UserInfoResponse;
import com.mbapps.fc.provider.service.RecipeService;
import com.mbapps.fc.provider.service.UserService;
import com.mbapps.fc.provider.util.VerificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    private static Logger LOGGER = LoggerFactory.getLogger(RecipeServiceController.class);

    private final RecipeService recipeService;

    private final UserService userService;

    private final VerificationUtil verificationUtil;

    public TestController(RecipeService recipeService, UserService userService, VerificationUtil verificationUtil) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.verificationUtil = verificationUtil;
    }


    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<UserInfoResponse> userAccess() {
        try {
            UserInfoResponse response = userService.getUserOnAuth();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred", e);
        }
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

}
