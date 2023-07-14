package com.mbapps.rc.provider.controller.impl;

import com.mbapps.rc.provider.controller.IRecipeServiceController;
import com.mbapps.rc.provider.domain.recipe.dto.InsertRecipeRequestDTO;
import com.mbapps.rc.provider.domain.recipe.dto.RecipeResponseDTO;
import com.mbapps.rc.provider.service.RecipeService;
import com.mbapps.rc.provider.util.VerificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/v1/recipeCentral")
@Validated
public class RecipeServiceController implements IRecipeServiceController {
    private static Logger LOGGER = LoggerFactory.getLogger(RecipeServiceController.class);

    private final RecipeService recipeService;
    private final VerificationUtil verificationUtil;

    public RecipeServiceController(RecipeService recipeService, VerificationUtil verificationUtil) {
        this.recipeService = recipeService;
        this.verificationUtil = verificationUtil;
    }

    @Override
    public ResponseEntity<RecipeResponseDTO> GetAllRecipes() {
        LOGGER.info("Received all recipes request");
        try {
            RecipeResponseDTO response = recipeService.getAllRecipes();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred", e);
        }
    }

    @Override
    public ResponseEntity<RecipeResponseDTO> InsertRecipe(InsertRecipeRequestDTO insertRequestDTO) {
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
