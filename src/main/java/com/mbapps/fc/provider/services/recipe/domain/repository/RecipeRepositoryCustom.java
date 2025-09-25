package com.mbapps.fc.provider.services.recipe.domain.repository;

import com.mbapps.fc.provider.services.recipe.domain.model.RecipePost;
import com.mbapps.fc.provider.services.recipe.domain.payload.request.RecipeFilters;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeRepositoryCustom {
    List<RecipePost> findFilteredRecipes(RecipeFilters filters, Pageable pageable);
    long countFilteredRecipes(RecipeFilters filters);
}