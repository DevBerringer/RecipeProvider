package com.mbapps.rc.provider.service;

import com.mbapps.rc.provider.controller.impl.RecipeServiceController;
import com.mbapps.rc.provider.domain.recipe.dto.InsertRecipeRequestDTO;
import com.mbapps.rc.provider.domain.recipe.dto.RecipeResponseDTO;
import com.mbapps.rc.provider.domain.recipe.mapper.RecipeMapper;
import com.mbapps.rc.provider.domain.recipe.model.RecipePost;
import com.mbapps.rc.provider.domain.recipe.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private static Logger LOGGER = LoggerFactory.getLogger(RecipeServiceController.class);
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeMapper recipeMapper;

    public RecipeResponseDTO insertRecipe(InsertRecipeRequestDTO insertRequestDTO) {

        RecipePost newRecipeDocument = RecipeMapper.insertRecipeDTOToRecipe(insertRequestDTO);

        LOGGER.info("Inserting Recipe for ID: " + newRecipeDocument.id().toString());

        recipeRepository.save(newRecipeDocument);


        return new RecipeResponseDTO().message("passed").success(true);
    }

    public RecipeResponseDTO getAllRecipes() {
        RecipeResponseDTO responseDto = new RecipeResponseDTO().success(false);
        try {
            List<RecipePost> recipePostList = recipeRepository.findAll();
            responseDto.recipeDTOs(recipeMapper.recipePostListToRecipeDtoList(recipePostList)).message("success").success(true);

            return responseDto;

        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }

    }
}
