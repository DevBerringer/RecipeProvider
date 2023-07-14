package com.mbapps.rc.provider.domain.recipe.dto;

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
public class RecipeResponseDTO {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Success")
    private boolean success;

    @JsonProperty("RecipeDTOs")
    private List<RecipeDTO> recipeDTOs;


}