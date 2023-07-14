package com.mbapps.rc.provider.domain.recipe.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "RecipePost")
@Accessors(fluent = true)
public class RecipePost {
    @Id
    private ObjectId id;

    private String picture;
    private String name;
    private Integer spicyLevel;
    private String description;
    private Integer cookTimeMin;
    private List<String> ingredients;
    private Integer difOfIngredient;
    private List<String> steps;
    private Double rating;
    private List<String> comments;
    private Date createdDate;

}

