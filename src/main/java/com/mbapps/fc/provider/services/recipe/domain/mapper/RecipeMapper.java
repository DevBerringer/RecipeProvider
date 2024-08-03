package com.mbapps.fc.provider.services.recipe.domain.mapper;

import com.mbapps.fc.provider.services.recipe.domain.dto.RecipeDTO;
import com.mbapps.fc.provider.services.recipe.domain.model.RecipePost;
import com.mbapps.fc.provider.services.recipe.domain.payload.request.InsertRecipeRequestDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RecipeMapper {

    public static RecipePost insertRecipeDTOToRecipe(InsertRecipeRequestDTO entity) {
        RecipePost newRecipe = new RecipePost();
        newRecipe.name(entity.name());
        newRecipe.picture(entity.picture());
        newRecipe.spicyLevel(entity.spicyLevel());
        newRecipe.description(entity.description());
        newRecipe.cookTimeMin(entity.cookTimeMin());
        newRecipe.prepTimeMin(entity.prepTimeMin());
        newRecipe.foodTypes(entity.foodTypes());
        newRecipe.ingredients(entity.ingredients());
        newRecipe.steps(entity.steps());
        newRecipe.createdBy(entity.createdBy());
        newRecipe.createdDate(new Date());

        return newRecipe;
    }

    public static List<RecipeDTO> recipePostListToRecipeDtoList(List<RecipePost> entity) {
        List<RecipeDTO> recipeDTOs = new ArrayList<>();

        for (RecipePost recipe : entity) {
            RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTO.id(recipe.id().toString());
            recipeDTO.name(recipe.name());
            recipeDTO.picture(recipe.picture());
            recipeDTO.spicyLevel(recipe.spicyLevel());
            recipeDTO.description(recipe.description());
            recipeDTO.prepTimeMin(recipe.prepTimeMin());
            recipeDTO.cookTimeMin(recipe.cookTimeMin());
            recipeDTO.foodTypes(recipe.foodTypes());
            recipeDTO.ingredients(recipe.ingredients());
            recipeDTO.steps(recipe.steps());
            recipeDTO.rating(recipe.rating());
            recipeDTO.comments(recipe.comments());
            recipeDTO.createdBy(recipe.createdBy());
            recipeDTO.createdDate(recipe.createdDate());

            recipeDTOs.add(recipeDTO);
        }

        return recipeDTOs;
    }
}
