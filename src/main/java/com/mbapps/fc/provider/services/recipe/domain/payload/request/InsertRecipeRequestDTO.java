package com.mbapps.fc.provider.services.recipe.domain.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class InsertRecipeRequestDTO {

    @NotBlank
    @JsonProperty("Name")
    private String name;

    @JsonProperty("FoodTypes")
    private List<String> foodTypes;

    @JsonProperty("MealTypes")
    private List<String> mealTypes;

    @JsonProperty("CuisineTypes")
    private List<String> cuisineTypes;

    @JsonProperty("IsVegetarian")
    private Boolean isVegetarian;

    @JsonProperty("SpicyLevel")
    private Boolean spicyLevel;

    @JsonProperty("CookTimeMin")
    private Integer cookTimeMin;

    @JsonProperty("PrepTimeMin")
    private Integer prepTimeMin;

    @JsonProperty("Serves")
    private Integer serves;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Ingredients")
    private List<String> ingredients;

    @JsonProperty("Steps")
    private List<String> steps;

    @JsonProperty("SelectedImage")
    private String selectedImage;

    @JsonProperty("Rating")
    private List<Integer> rating;

    @JsonProperty("CreatedBy")
    private String createdBy;

}
