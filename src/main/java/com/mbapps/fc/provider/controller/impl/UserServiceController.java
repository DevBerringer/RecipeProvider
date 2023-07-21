package com.mbapps.fc.provider.controller.impl;

import com.mbapps.fc.provider.payload.response.AllUsersResponseDTO;
import com.mbapps.fc.provider.payload.response.RecipeResponseDTO;
import com.mbapps.fc.provider.service.UserService;
import com.mbapps.fc.provider.util.VerificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserServiceController  {
    private static Logger LOGGER = LoggerFactory.getLogger(RecipeServiceController.class);

    @Autowired
    private final UserService userService;

    @Autowired
    private final VerificationUtil verificationUtil;

    public UserServiceController(UserService userService, VerificationUtil verificationUtil) {
        this.userService = userService;
        this.verificationUtil = verificationUtil;
    }

    @GetMapping("/all")
    public ResponseEntity<AllUsersResponseDTO> GetAllRecipes() {
        LOGGER.info("Received all recipes request");
        try {
            AllUsersResponseDTO response = userService.getAllUsers();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred", e);
        }    }

}
