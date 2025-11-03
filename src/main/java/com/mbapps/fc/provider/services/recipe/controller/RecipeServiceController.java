package com.mbapps.fc.provider.services.recipe.controller;

import com.mbapps.fc.provider.services.recipe.domain.dto.RecipeDTO;
import com.mbapps.fc.provider.services.recipe.domain.payload.request.InsertRecipeRequestDTO;
import com.mbapps.fc.provider.services.recipe.domain.payload.request.RecipeFilters;
import com.mbapps.fc.provider.services.recipe.domain.payload.response.RecipeResponseDTO;
import com.mbapps.fc.provider.services.recipe.service.RecipeService;
import com.mbapps.fc.provider.util.VerificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/recipe")
public class RecipeServiceController  {
    private static Logger LOGGER = LoggerFactory.getLogger(RecipeServiceController.class);

    private final RecipeService recipeService;
    private final VerificationUtil verificationUtil;

    @Autowired
    public RecipeServiceController(RecipeService recipeService, VerificationUtil verificationUtil) {
        this.recipeService = recipeService;
        this.verificationUtil = verificationUtil;
    }

    @GetMapping("/paged")
    public ResponseEntity<RecipeResponseDTO> getPagedRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @ModelAttribute RecipeFilters filters) {

        LOGGER.info("Received paginated recipes request: page {}, size {}, filters: {}", page, pageSize, filters);

        try {
            RecipeResponseDTO response = recipeService.getPagedRecipes(page, pageSize, filters);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipesById(@PathVariable("id") String id) {
        LOGGER.info("Received recipe: {}", id);
        try {
            RecipeDTO response = recipeService.getById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred", e);
        }
    }


    @PostMapping("/insert")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecipeResponseDTO> InsertRecipe(@RequestBody InsertRecipeRequestDTO insertRequestDTO) {
        LOGGER.info("Received recipe insert Request");
        try {
            if (verificationUtil.validateInsertRequest(insertRequestDTO)) {
                RecipeResponseDTO response = recipeService.insertRecipe(insertRequestDTO);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred", e);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }
}
