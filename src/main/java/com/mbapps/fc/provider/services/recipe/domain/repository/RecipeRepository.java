package com.mbapps.fc.provider.services.recipe.domain.repository;

import com.mbapps.fc.provider.services.recipe.domain.model.RecipePost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends MongoRepository<RecipePost, String> {


}
