package com.mbapps.fc.provider.services.user.domain.mapper;

import com.mbapps.fc.provider.services.recipe.domain.dto.UserDTO;
import com.mbapps.fc.provider.services.recipe.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public List<UserDTO> userListToUserDtoList(List<User> entity) {
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : entity) {
            UserDTO userDTO = userToUserDTO(user);
            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.id(user.getId());
        userDTO.username(user.getUsername());
        userDTO.email(user.getEmail());
        userDTO.groups(new ArrayList<>(user.getGroups()));
        userDTO.description(user.getDescription());
        userDTO.imagePath(user.getImagePath());

        return userDTO;
    }
}
