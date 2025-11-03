package com.mbapps.fc.provider.services.user.controller;

import com.mbapps.fc.provider.services.user.domain.payload.request.UpdateProfileRequest;
import com.mbapps.fc.provider.services.user.domain.payload.response.AllUsersResponseDTO;
import com.mbapps.fc.provider.services.user.domain.payload.response.UserInfoResponse;
import com.mbapps.fc.provider.services.user.service.UserService;
import com.mbapps.fc.provider.util.VerificationUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserServiceController  {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceController.class);

    private final UserService userService;
    private final VerificationUtil verificationUtil;

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

    @PostMapping("/updateProfile")
    public ResponseEntity<UserInfoResponse> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        LOGGER.info("Update request for user: {}", updateProfileRequest.getId());
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
