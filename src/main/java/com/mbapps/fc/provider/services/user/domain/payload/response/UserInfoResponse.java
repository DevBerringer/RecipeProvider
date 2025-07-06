package com.mbapps.fc.provider.services.user.domain.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mbapps.fc.provider.services.recipe.domain.model.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.ResponseCookie;

import java.util.List;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class UserInfoResponse {
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

    @JsonIgnore
    private ResponseCookie cookie;

}
