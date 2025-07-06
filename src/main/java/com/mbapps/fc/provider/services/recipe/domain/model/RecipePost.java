package com.mbapps.fc.provider.services.recipe.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "RecipePost")
public class RecipePost {
    @Id
    private String id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    private boolean isSpicy;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotBlank
    private double prepTimeMin;

    @NotBlank
    private double cookTimeMin;

    @NotBlank
    private List<String> ingredients;

    @NotBlank
    private List<String> steps;

    @NotBlank
    private List<String> foodTypes;

    private List<String> mealTypes;

    private List<String> cuisineTypes;

    private boolean isVegetarian;

    private Integer serves;

    private String picture;

    private List<Integer> rating;

    private List<String> comments;

    private String createdBy;

    private Date createdDate;
}
