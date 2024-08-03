package com.mbapps.fc.provider.services.recipe.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Document(collection = "Roles")
public class Role {
    @Id
    private String id;

    private ERole name;
}
