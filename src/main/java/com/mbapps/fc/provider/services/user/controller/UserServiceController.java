package com.mbapps.fc.provider.services.user.controller;

import com.mbapps.fc.provider.services.recipe.controller.RecipeServiceController;
import com.mbapps.fc.provider.services.user.domain.payload.request.UpdateProfileRequest;
import com.mbapps.fc.provider.services.user.domain.payload.response.AllUsersResponseDTO;
import com.mbapps.fc.provider.services.user.domain.payload.response.UserInfoResponse;
import com.mbapps.fc.provider.services.user.service.UserService;
import com.mbapps.fc.provider.util.VerificationUtil;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PathParam;
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
    public ResponseEntity<AllUsersResponseDTO> GetAllUsers() {
        LOGGER.info("Received all Users request");
        try {
            AllUsersResponseDTO response = userService.getAllUsers();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred", e);
        }
    }

    @PostMapping("/user/{email}")
    public ResponseEntity<UserInfoResponse> GetUser(@PathParam("email") String email) {
        LOGGER.info("Received all Users request");
        try {
            UserInfoResponse response = userService.getCurrentUser(email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred", e);
        }
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<UserInfoResponse> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        LOGGER.info("Update requset for user: " + updateProfileRequest.Id());
        try {
            UserInfoResponse response = userService.updateUser(updateProfileRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred", e);
        }
    }

}
