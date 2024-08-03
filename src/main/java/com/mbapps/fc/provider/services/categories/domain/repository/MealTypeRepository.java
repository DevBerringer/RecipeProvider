package com.mbapps.fc.provider.services.categories.domain.repository;

import com.mbapps.fc.provider.services.categories.domain.model.MealType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealTypeRepository extends MongoRepository<MealType, String> {


}
