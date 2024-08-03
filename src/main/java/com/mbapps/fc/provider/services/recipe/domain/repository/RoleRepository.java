package com.mbapps.fc.provider.services.recipe.domain.repository;

import java.util.Optional;

import com.mbapps.fc.provider.services.recipe.domain.model.ERole;
import com.mbapps.fc.provider.services.recipe.domain.model.Role;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
