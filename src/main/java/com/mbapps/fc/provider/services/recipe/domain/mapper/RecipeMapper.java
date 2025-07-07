package com.mbapps.fc.provider.services.recipe.domain.mapper;

import com.mbapps.fc.provider.services.recipe.domain.dto.RecipeDTO;
import com.mbapps.fc.provider.services.recipe.domain.model.RecipePost;
import com.mbapps.fc.provider.services.recipe.domain.payload.request.InsertRecipeRequestDTO;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RecipeMapper {

    public static RecipePost insertRecipeDTOToRecipe(InsertRecipeRequestDTO entity) {
        return RecipePost.builder()
                .name(entity.getName())
                .mealTypes(entity.getMealTypes())
                .picture(entity.getSelectedImage())
                .isSpicy(entity.getSpicyLevel())
                .description(entity.getDescription())
                .cookTimeMin(entity.getCookTimeMin())
                .prepTimeMin(entity.getPrepTimeMin())
                .foodTypes(entity.getFoodTypes())
                .ingredients(entity.getIngredients())
                .steps(entity.getSteps())
                .isVegetarian(entity.getIsVegetarian())
                .serves(entity.getServes())
                .cuisineTypes(entity.getCuisineTypes())
                .rating(entity.getRating())
                .createdBy(entity.getCreatedBy())
                .createdDate(new Date())
                .build();
    }


    public static List<RecipeDTO> recipePostListToRecipeDtoList(List<RecipePost> entity) {
        return entity.stream()
                .map(recipe -> RecipeDTO.builder()
                        .id(recipe.getId())
                        .name(recipe.getName())
                        .selectedImage(recipe.getPicture())
                        .isSpicy(recipe.isSpicy())
                        .description(recipe.getDescription())
                        .prepTimeMin(recipe.getPrepTimeMin())
                        .cookTimeMin(recipe.getCookTimeMin())
                        .foodTypes(recipe.getFoodTypes())
                        .ingredients(recipe.getIngredients())
                        .steps(recipe.getSteps())
                        .rating(recipe.getRating())
                        .comments(recipe.getComments())
                        .createdBy(recipe.getCreatedBy())
                        .createdDate(recipe.getCreatedDate())
                        .mealTypes(recipe.getMealTypes())
                        .cuisineTypes(recipe.getCuisineTypes())
                        .isVegetarian(recipe.isVegetarian())
                        .serves(recipe.getServes())
                        .build())
                .toList();
    }

    public static RecipeDTO recipePostToRecipeDTO(RecipePost recipePost) {
        if (recipePost == null) {
            return null;
        }

        return RecipeDTO.builder()
                .id(recipePost.getId())
                .name(recipePost.getName())
                .selectedImage(recipePost.getPicture())
                .isSpicy(recipePost.isSpicy())
                .description(recipePost.getDescription())
                .prepTimeMin(recipePost.getPrepTimeMin())
                .cookTimeMin(recipePost.getCookTimeMin())
                .foodTypes(recipePost.getFoodTypes())
                .mealTypes(recipePost.getMealTypes())
                .cuisineTypes(recipePost.getCuisineTypes())
                .isVegetarian(recipePost.isVegetarian())
                .serves(recipePost.getServes())
                .ingredients(recipePost.getIngredients())
                .steps(recipePost.getSteps())
                .rating(recipePost.getRating())
                .comments(recipePost.getComments())
                .createdBy(recipePost.getCreatedBy())
                .createdDate(recipePost.getCreatedDate())
                .build();
    }
}
