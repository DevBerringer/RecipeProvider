package com.mbapps.fc.provider.service;

import com.mbapps.fc.provider.controller.impl.RecipeServiceController;
import com.mbapps.fc.provider.payload.request.InsertRecipeRequestDTO;
import com.mbapps.fc.provider.payload.response.RecipeResponseDTO;
import com.mbapps.fc.provider.domain.recipe.mapper.RecipeMapper;
import com.mbapps.fc.provider.domain.recipe.model.RecipePost;
import com.mbapps.fc.provider.domain.recipe.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private static Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeMapper recipeMapper;

    public RecipeResponseDTO insertRecipe(InsertRecipeRequestDTO insertRequestDTO) {

        //UserDetails userDetails =
         //       (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // userDetails.getUsername()
            // userDetails.getPassword()
            // userDetails.getAuthorities()


        RecipePost newRecipeDocument = RecipeMapper.insertRecipeDTOToRecipe(insertRequestDTO);

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
