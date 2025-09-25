package com.mbapps.fc.provider.services.recipe.domain.repository;

import com.mbapps.fc.provider.services.recipe.domain.model.RecipePost;
import com.mbapps.fc.provider.services.recipe.domain.payload.request.RecipeFilters;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class RecipeRepositoryImpl implements RecipeRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<RecipePost> findFilteredRecipes(RecipeFilters filters, Pageable pageable) {
        Query query = buildQuery(filters).with(pageable);
        return mongoTemplate.find(query, RecipePost.class);
    }

    @Override
    public long countFilteredRecipes(RecipeFilters filters) {
        Query query = buildQuery(filters);
        return mongoTemplate.count(query, RecipePost.class);
    }

    private Query buildQuery(RecipeFilters filters) {
        Query query = new Query();

        if (filters.getSearch() != null && !filters.getSearch().isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(filters.getSearch(), "i"));
        }
        if (filters.getMeals() != null && !filters.getMeals().isEmpty()) {
            query.addCriteria(Criteria.where("mealTypes").in(filters.getMeals()));
        }
        if (filters.getFoods() != null && !filters.getFoods().isEmpty()) {
            query.addCriteria(Criteria.where("foodTypes").in(filters.getFoods()));
        }
        if (filters.getRegions() != null && !filters.getRegions().isEmpty()) {
            query.addCriteria(Criteria.where("cuisineTypes").in(filters.getRegions()));
        }
        if (filters.getVegetarian() != null) {
            query.addCriteria(Criteria.where("isVegetarian").is(filters.getVegetarian()));
        }
        if (filters.getSpicy() != null) {
            query.addCriteria(Criteria.where("isSpicy").is(filters.getSpicy()));
        }
        if (filters.getMaxCookTime() != null) {
            query.addCriteria(Criteria.where("cookTime").lte(filters.getMaxCookTime()));
        }

        return query;
    }
}
