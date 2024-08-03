package com.mbapps.fc.provider.services.recipe.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Document(collection = "Group")
public class Group {

    @Id
    private String id;

    @NotBlank
    @Size(max = 25)
    private String name;

    @NotBlank
    @DBRef
    private User owner;

    @DBRef
    private Set<User> members = new HashSet<>();
}
