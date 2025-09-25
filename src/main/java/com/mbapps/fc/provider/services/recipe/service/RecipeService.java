package com.mbapps.fc.provider.services.recipe.service;

import com.mbapps.fc.provider.services.recipe.domain.dto.RecipeDTO;
import com.mbapps.fc.provider.services.recipe.domain.payload.request.InsertRecipeRequestDTO;
import com.mbapps.fc.provider.services.recipe.domain.payload.request.RecipeFilters;
import com.mbapps.fc.provider.services.recipe.domain.payload.response.RecipeResponseDTO;
import com.mbapps.fc.provider.services.recipe.domain.mapper.RecipeMapper;
import com.mbapps.fc.provider.services.recipe.domain.model.RecipePost;
import com.mbapps.fc.provider.services.recipe.domain.repository.RecipeRepository;
import com.mbapps.fc.provider.security.services.UserDetailsImpl;
import com.mbapps.fc.provider.services.recipe.domain.repository.RecipeRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public RecipeDTO getById(String id) {
        try {
            Optional<RecipePost> recipePost = recipeRepository.findById(id);
            if (recipePost.isPresent()) {
                return RecipeMapper.recipePostToRecipeDTO(recipePost.get());
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
        return RecipeDTO.builder().build();
    }

    public RecipeResponseDTO getAllRecipes() {
        RecipeResponseDTO responseDto = new RecipeResponseDTO().success(false);
        try {
            List<RecipePost> recipePostList = recipeRepository.findAll();
            responseDto.recipeDTOs(
                    RecipeMapper.recipePostListToRecipeDtoList(recipePostList))
                    .message("success").success(true);

            return responseDto;

        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
    }

    public RecipeResponseDTO getPagedRecipes(int page, int pageSize, RecipeFilters filters) {
        RecipeResponseDTO responseDto = new RecipeResponseDTO().success(false);
        try {
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "name"));
            List<RecipePost> recipePostList = recipeRepository.findFilteredRecipes(filters, pageable);
            long total = recipeRepository.countFilteredRecipes(filters);

            responseDto.recipeDTOs(RecipeMapper.recipePostListToRecipeDtoList(recipePostList))
                    .message("success")
                    .success(true)
                    .totalCount(total);

            return responseDto;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
    }

    public RecipeResponseDTO insertRecipe(InsertRecipeRequestDTO insertRequestDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.getId().equals(insertRequestDTO.getCreatedBy())) {
            return new RecipeResponseDTO().message("failed").success(false);
        }

        RecipePost newRecipeDocument = RecipeMapper.insertRecipeDTOToRecipe(insertRequestDTO);
        recipeRepository.save(newRecipeDocument);

        return new RecipeResponseDTO().message("passed").success(true);
    }
}
