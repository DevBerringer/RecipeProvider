package com.mbapps.fc.provider.domain.recipe.repository;

import java.util.Optional;

import com.mbapps.fc.provider.domain.recipe.model.ERole;
import com.mbapps.fc.provider.domain.recipe.model.Role;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
