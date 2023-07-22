package com.mbapps.fc.provider.service;

import com.mbapps.fc.provider.controller.impl.RecipeServiceController;
import com.mbapps.fc.provider.domain.recipe.mapper.UserMapper;
import com.mbapps.fc.provider.domain.recipe.model.RecipePost;
import com.mbapps.fc.provider.domain.recipe.model.User;
import com.mbapps.fc.provider.domain.recipe.repository.UserRepository;
import com.mbapps.fc.provider.payload.response.AllUsersResponseDTO;
import com.mbapps.fc.provider.payload.response.AuthResponse;
import com.mbapps.fc.provider.payload.response.RecipeResponseDTO;
import com.mbapps.fc.provider.payload.response.UserInfoResponse;
import com.mbapps.fc.provider.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

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

    public UserInfoResponse getUserOnAuth() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return new UserInfoResponse(
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles);

        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
    }
}
