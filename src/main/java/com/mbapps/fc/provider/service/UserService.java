package com.mbapps.fc.provider.service;

import com.mbapps.fc.provider.controller.impl.RecipeServiceController;
import com.mbapps.fc.provider.domain.recipe.mapper.UserMapper;
import com.mbapps.fc.provider.domain.recipe.model.RecipePost;
import com.mbapps.fc.provider.domain.recipe.model.User;
import com.mbapps.fc.provider.domain.recipe.repository.UserRepository;
import com.mbapps.fc.provider.payload.response.AllUsersResponseDTO;
import com.mbapps.fc.provider.payload.response.RecipeResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
