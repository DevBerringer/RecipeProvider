package com.mbapps.fc.provider.services.user.domain.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class UpdateProfileRequest {

    @NotBlank
    @JsonProperty("Id")
    private String Id;

    @NotBlank
    @JsonProperty("ImagePath")
    private String imagePath;

    @NotBlank
    @JsonProperty("Description")
    private String description;

}
