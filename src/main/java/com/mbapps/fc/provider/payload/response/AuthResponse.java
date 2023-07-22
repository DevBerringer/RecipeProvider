package com.mbapps.fc.provider.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AuthResponse {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Username")
    private String username;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Roles")
    private List<String> roles;
}
