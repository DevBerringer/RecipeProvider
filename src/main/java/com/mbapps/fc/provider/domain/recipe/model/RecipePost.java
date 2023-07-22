package com.mbapps.fc.provider.domain.recipe.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "RecipePost")
@Accessors(fluent = true)
public class RecipePost {
    @Id
    private String id;

    private String picture;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    private boolean spicyLevel;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotBlank
    private double prepTimeMin;

    @NotBlank
    private double cookTimeMin;

    @NotBlank
    private List<String> ingredients;

    private Integer difOfIngredient;

    @NotBlank
    private List<String> steps;

    @NotBlank
    private List<String> foodType;

    private List<Double> rating;

    private List<String> comments;

    private String createdBy;

    private Date createdDate;
}

