package com.mbapps.fc.provider.domain.recipe.mapper;

import com.mbapps.fc.provider.payload.request.InsertRecipeRequestDTO;
import com.mbapps.fc.provider.domain.recipe.dto.RecipeDTO;
import com.mbapps.fc.provider.domain.recipe.model.RecipePost;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeMapper {

    public static RecipePost insertRecipeDTOToRecipe(InsertRecipeRequestDTO entity) {
        RecipePost newRecipe = new RecipePost();
        newRecipe.id(entity.id());
        newRecipe.name(entity.name());
        newRecipe.picture(entity.picture());
        newRecipe.spicyLevel(entity.spicyLevel());
        newRecipe.description(entity.description());
        newRecipe.cookTimeMin(entity.cookTimeMin());
        newRecipe.ingredients(entity.ingredients());
        newRecipe.difOfIngredient(entity.difOfIngredient());
        newRecipe.steps(entity.steps());
        newRecipe.rating(entity.rating());

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
            recipeDTO.cookTimeMin(recipe.cookTimeMin());
            recipeDTO.ingredients(recipe.ingredients());
            recipeDTO.difOfIngredient(recipe.difOfIngredient());
            recipeDTO.steps(recipe.steps());
            recipeDTO.rating(recipe.rating());
            recipeDTO.comments(recipe.comments());
            recipeDTO.createdDate(recipe.createdDate());

            recipeDTOs.add(recipeDTO);
        }

        return recipeDTOs;
    }
}
