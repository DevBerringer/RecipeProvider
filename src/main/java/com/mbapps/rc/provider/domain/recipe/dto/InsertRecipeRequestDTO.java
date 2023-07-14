package com.mbapps.rc.provider.domain.recipe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class InsertRecipeRequestDTO {

    @JsonProperty("id")
    private ObjectId id;

    @JsonProperty("createdBy")
    private ObjectId createdBy;

    @JsonProperty("Picture")
    private String picture;

    @JsonProperty("name")
    private String name;

    @JsonProperty("spicyLevel")
    private int spicyLevel;

    @JsonProperty("description")
    private String description;

    @JsonProperty("cookTimeMin")
    private int cookTimeMin;

    @JsonProperty("ingredients")
    private List<String> ingredients;

    @JsonProperty("difOfIngredient")
    private int difOfIngredient;

    @JsonProperty("steps")
    private List<String> steps;

    @JsonProperty("rating")
    private double rating;
}
