package com.mbapps.fc.provider.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class InsertRecipeRequestDTO {

    @NotBlank
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Picture")
    private String picture;

    @JsonProperty("SpicyLevel")
    private Boolean spicyLevel;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("CookTimeMin")
    private Integer cookTimeMin;

    @JsonProperty("PrepTimeMin")
    private Integer prepTimeMin;

    @JsonProperty("FoodTypes")
    private List<String> foodTypes;

    @JsonProperty("Ingredients")
    private List<String> ingredients;

    @JsonProperty("Steps")
    private List<String> steps;

    @JsonProperty("CreatedBy")
    private String createdBy;

}
