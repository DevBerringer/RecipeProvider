package com.mbapps.fc.provider.domain.recipe.mapper;

import com.mbapps.fc.provider.domain.recipe.dto.UserDTO;
import com.mbapps.fc.provider.domain.recipe.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public List<UserDTO> userListToUserDtoList(List<User> entity) {
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : entity) {
            UserDTO userDTO = new UserDTO();

            userDTO.id(user.getId());
            userDTO.username(user.getUsername());
            userDTO.email(user.getEmail());
           // userDTO.groups(user.getGroups());

            userDTOs.add(userDTO);
        }

        return userDTOs;
    }
}
