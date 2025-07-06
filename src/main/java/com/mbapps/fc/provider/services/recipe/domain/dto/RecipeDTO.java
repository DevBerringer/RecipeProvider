package com.mbapps.fc.provider.services.recipe.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class RecipeDTO {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Picture")
    private String picture;

    @JsonProperty("isSpicy")
    private boolean isSpicy;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("PrepTimeMin")
    private double prepTimeMin;

    @JsonProperty("CookTimeMin")
    private double cookTimeMin;

    @JsonProperty("FoodTypes")
    private List<String> foodTypes;

    @JsonProperty("MealTypes")
    private List<String> mealTypes;

    @JsonProperty("CuisineTypes")
    private List<String> cuisineTypes;

    @JsonProperty("IsVegetarian")
    private boolean isVegetarian;

    @JsonProperty("Serves")
    private Integer serves;

    @JsonProperty("Ingredients")
    private List<String> ingredients;

    @JsonProperty("Steps")
    private List<String> steps;

    @JsonProperty("Rating")
    private List<Integer> rating;

    @JsonProperty("Comments")
    private List<String> comments;

    @JsonProperty("CreatedBy")
    private String createdBy;

    @JsonProperty("CreatedDate")
    private Date createdDate;
}
