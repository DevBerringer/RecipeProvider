package com.mbapps.fc.provider.services.categories.domain.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mbapps.fc.provider.services.categories.domain.model.FoodType;
import com.mbapps.fc.provider.services.categories.domain.model.MealType;
import com.mbapps.fc.provider.services.categories.domain.model.RegionType;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class CategoriesResponseDto {

    @JsonProperty("RegionCategories")
    private List<RegionType> regionCategories;

    @JsonProperty("FoodCategories")
    private List<FoodType> foodCategories;

    @JsonProperty("MealCategories")
    private List<MealType> mealCategories;
}
