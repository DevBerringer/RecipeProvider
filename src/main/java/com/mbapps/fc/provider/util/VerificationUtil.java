package com.mbapps.fc.provider.util;

import com.mbapps.fc.provider.services.recipe.domain.payload.request.InsertRecipeRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class VerificationUtil {
    public boolean validateInsertRequest(InsertRecipeRequestDTO insertRequestDTO) {

        return true;
    }
}


