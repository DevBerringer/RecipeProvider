package com.mbapps.fc.provider.services.recipe.domain.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class RecipeFilters {
    private String search;
    private List<String> meals;
    private List<String> foods;
    private List<String> regions;
    private Boolean vegetarian;
    private Boolean spicy;
    private Integer maxCookTime;

}