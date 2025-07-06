package com.mbapps.fc.provider.services.user.service;

import com.mbapps.fc.provider.services.user.domain.mapper.UserMapper;
import com.mbapps.fc.provider.services.recipe.domain.model.User;
import com.mbapps.fc.provider.services.recipe.domain.repository.UserRepository;
import com.mbapps.fc.provider.services.user.domain.payload.request.UpdateProfileRequest;
import com.mbapps.fc.provider.services.user.domain.payload.response.AllUsersResponseDTO;
import com.mbapps.fc.provider.services.user.domain.payload.response.UserInfoResponse;
import com.mbapps.fc.provider.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public AllUsersResponseDTO getAllUsers() {
        AllUsersResponseDTO responseDto = new AllUsersResponseDTO().success(false);
        try {
            List<User> userList = userRepository.findAll();
            responseDto.userDTOs(userMapper.userListToUserDtoList(userList)).message("success").success(true);

            return responseDto;

        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
    }

    public UserInfoResponse getCurrentUser(String email) {
//        UserDTO userDTO = new UserDTO();
//        Optional<User> user = userRepository.getByEmail(email);
//
//        if (user.isPresent()) {
//            userDTO = userMapper.userToUserDTO(user.get());
//        }
//
//        return UserInfoResponse;
    return null;

    }

    public UserInfoResponse getUserOnAuth() {

        try {
            UserDetailsImpl userDetails = getAuth();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            Optional<User> authenticatedUser = userRepository.findById(userDetails.getId());

            if (authenticatedUser.isPresent()) {
                User user = authenticatedUser.get();
                return new UserInfoResponse()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .description(user.getDescription())
                        .imagePath(user.getImagePath())
                        .groups(new ArrayList<>(user.getGroups()))
                        .roles(roles);
            }
            return null;

        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
    }

    public UserInfoResponse updateUser(UpdateProfileRequest updateProfileRequest) {
        UserDetailsImpl userDetails = getAuth();

        Optional<User> existingUser = userRepository.findById(updateProfileRequest.getId());
        if (existingUser.isPresent() && userDetails.getId().equals(updateProfileRequest.getId())) {
            User user = existingUser.get();
            user.setImagePath(updateProfileRequest.getImagePath());
            user.setDescription(updateProfileRequest.getDescription());

            userRepository.save(user);
        } else {
            // Handle the case when the user with the given ID is not found.
            // You can choose to throw an exception or take appropriate action.
            return null;
        }

        return new UserInfoResponse()
                    .description(updateProfileRequest.getDescription())
                    .imagePath(updateProfileRequest.getImagePath());
    }

    private UserDetailsImpl getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails;
    }
}
