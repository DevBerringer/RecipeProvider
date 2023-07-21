package com.mbapps.fc.provider.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mbapps.fc.provider.domain.recipe.dto.RecipeDTO;
import com.mbapps.fc.provider.domain.recipe.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class AllUsersResponseDTO {
    @JsonProperty("Message")
    private String message;

    @JsonProperty("Success")
    private boolean success;

    @JsonProperty("UserDTOs")
    private List<UserDTO> userDTOs;
}
