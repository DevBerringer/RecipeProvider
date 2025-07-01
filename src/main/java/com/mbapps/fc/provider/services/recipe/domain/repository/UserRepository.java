package com.mbapps.fc.provider.services.recipe.domain.repository;

import java.util.Optional;

import com.mbapps.fc.provider.services.recipe.domain.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<User> getByEmail(String email);

    Boolean existsByEmail(String email);
}
