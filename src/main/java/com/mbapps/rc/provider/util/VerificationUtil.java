package com.mbapps.rc.provider.util;

import com.mbapps.rc.provider.domain.recipe.dto.InsertRecipeRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class VerificationUtil {
    public boolean validateInsertRequest(InsertRecipeRequestDTO insertRequestDTO) {

        return true;
    }
}


