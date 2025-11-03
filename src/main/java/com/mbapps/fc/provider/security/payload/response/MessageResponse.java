package com.mbapps.fc.provider.security.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageResponse {
    @JsonProperty("Message")
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
