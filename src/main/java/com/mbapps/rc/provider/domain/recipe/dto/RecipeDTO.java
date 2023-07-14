package com.mbapps.rc.provider.domain.recipe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;

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
    private int spicyLevel;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("CookTimeMin")
    private int cookTimeMin;

    @JsonProperty("Ingredients")
    private List<String> ingredients;

    @JsonProperty("DifOfIngredient")
    private Integer difOfIngredient;

    @JsonProperty("Steps")
    private List<String> steps;

    @JsonProperty("rating")
    private Double rating;

    @JsonProperty("Comments")
    private List<String> comments;

    @JsonProperty("createdDate")
    private Date createdDate;
}
