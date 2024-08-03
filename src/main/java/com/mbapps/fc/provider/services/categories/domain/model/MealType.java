package com.mbapps.fc.provider.services.categories.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Document(collection = "MealType")
public class MealType {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String ImgPath;
}
