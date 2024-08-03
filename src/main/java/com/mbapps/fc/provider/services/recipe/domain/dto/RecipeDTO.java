package com.mbapps.fc.provider.services.recipe.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class RecipeDTO {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Picture")
    private String picture;

    @JsonProperty("SpicyLevel")
    private boolean spicyLevel;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("PrepTimeMin")
    private double prepTimeMin;

    @JsonProperty("CookTimeMin")
    private double cookTimeMin;

    @JsonProperty("FoodTypes")
    private List<String> foodTypes;

    @JsonProperty("Ingredients")
    private List<String> ingredients;

    @JsonProperty("Steps")
    private List<String> steps;

    @JsonProperty("Rating")
    private List<Double> rating;

    @JsonProperty("Comments")
    private List<String> comments;

    @JsonProperty("CreatedBy")
    private String createdBy;

    @JsonProperty("CreatedDate")
    private Date createdDate;
}
