package com.mbapps.fc.provider.controller.impl;

import com.mbapps.fc.provider.payload.request.InsertRecipeRequestDTO;
import com.mbapps.fc.provider.payload.response.RecipeResponseDTO;
import com.mbapps.fc.provider.service.RecipeService;
import com.mbapps.fc.provider.util.VerificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public RecipeServiceController(RecipeService recipeService, VerificationUtil verificationUtil) {
        this.recipeService = recipeService;
        this.verificationUtil = verificationUtil;
    }

    @GetMapping("/all")
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
