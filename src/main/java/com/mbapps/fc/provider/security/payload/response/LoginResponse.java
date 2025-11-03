package com.mbapps.fc.provider.security.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mbapps.fc.provider.services.user.domain.payload.response.UserInfoResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = true, chain = true)
public class LoginResponse extends UserInfoResponse {
    
    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("Message")
    private String message;
}

