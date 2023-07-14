package com.mbapps.rc.provider.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbapps.rc.provider.domain.recipe.dto.InsertRecipeRequestDTO;
import com.mbapps.rc.provider.domain.recipe.dto.RecipeResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Recipe Central API
 */
@Api(value = "Recipe Central Service")
public interface IRecipeServiceController {

    @ApiOperation(value = "Get all Recipes Data service")
    @GetMapping("/recipes")
    ResponseEntity<RecipeResponseDTO> GetAllRecipes();


    @ApiOperation(value = "Insert Recipe Data service")
    @PostMapping(value = "/insert/recipe", produces = {"application/json"})
    ResponseEntity<RecipeResponseDTO> InsertRecipe(@RequestBody InsertRecipeRequestDTO insertRequestDTO) throws JsonProcessingException;



}
