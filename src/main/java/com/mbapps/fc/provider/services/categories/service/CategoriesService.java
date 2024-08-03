package com.mbapps.fc.provider.services.categories.service;

import com.mbapps.fc.provider.services.categories.domain.payload.response.CategoriesResponseDto;
import com.mbapps.fc.provider.services.categories.domain.repository.FoodTypeRepository;
import com.mbapps.fc.provider.services.categories.domain.repository.MealTypeRepository;
import com.mbapps.fc.provider.services.categories.domain.repository.RegionTypeRepository;
import com.mbapps.fc.provider.services.recipe.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriesService {

    private static Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);
    @Autowired
    private FoodTypeRepository foodTypeRepository;

    @Autowired
    private MealTypeRepository mealTypeRepository;

    @Autowired
    private RegionTypeRepository regionTypeRepository;

    public CategoriesService(FoodTypeRepository foodTypeRepository, MealTypeRepository mealTypeRepository, RegionTypeRepository regionTypeRepository) {
        this.foodTypeRepository = foodTypeRepository;
        this.mealTypeRepository = mealTypeRepository;
        this.regionTypeRepository = regionTypeRepository;
    }

    public CategoriesResponseDto getAllCategories() {
        CategoriesResponseDto responseDto = new CategoriesResponseDto();
        responseDto.foodCategories(foodTypeRepository.findAll());
        responseDto.mealCategories(mealTypeRepository.findAll());
        responseDto.regionCategories(regionTypeRepository.findAll());

        return responseDto;
    }
}
