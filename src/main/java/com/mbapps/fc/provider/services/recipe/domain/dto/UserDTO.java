package com.mbapps.fc.provider.services.recipe.domain.dto;

import com.mbapps.fc.provider.services.recipe.domain.model.Group;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class UserDTO {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Username")
    private String username;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Roles")
    private List<String> roles;

    @JsonProperty("ImagePath")
    private String imagePath;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Groups")
    private List<Group> groups;
}
