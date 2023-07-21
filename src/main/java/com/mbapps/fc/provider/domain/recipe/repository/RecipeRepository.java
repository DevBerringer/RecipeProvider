package com.mbapps.fc.provider.domain.recipe.repository;

import com.mbapps.fc.provider.domain.recipe.model.RecipePost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends MongoRepository<RecipePost, String> {


}
