package com.mbapps.fc.provider.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class InsertRecipeRequestDTO {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Picture")
    private String picture;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("SpicyLevel")
    private boolean spicyLevel;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("CookTimeMin")
    private double cookTimeMin;

    @JsonProperty("Ingredients")
    private List<String> ingredients;

    @JsonProperty("DifOfIngredient")
    private int difOfIngredient;

    @JsonProperty("Steps")
    private List<String> steps;

    @JsonProperty("Rating")
    private double rating;

    @JsonProperty("Comments")
    private double comments;

    @JsonProperty("createdBy")
    private ObjectId createdBy;

    @JsonProperty("CreatedDate")
    private ObjectId createdDate;

    @JsonProperty("username")
    public String username;
}
