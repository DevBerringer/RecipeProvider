package com.mbapps.fc.provider.services.categories.domain.repository;

import com.mbapps.fc.provider.services.categories.domain.model.RegionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionTypeRepository extends MongoRepository<RegionType, String> {


}

