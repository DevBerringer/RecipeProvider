package com.mbapps.fc.provider.services.categories.controller;

import com.mbapps.fc.provider.services.categories.domain.payload.response.CategoriesResponseDto;
import com.mbapps.fc.provider.services.categories.service.CategoriesService;
import com.mbapps.fc.provider.util.VerificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    private static Logger LOGGER = LoggerFactory.getLogger(CategoriesController.class);

    private final CategoriesService categoriesService;
    private final VerificationUtil verificationUtil;

    public CategoriesController(CategoriesService categoriesService, VerificationUtil verificationUtil) {
        this.categoriesService = categoriesService;
        this.verificationUtil = verificationUtil;
    }

    @GetMapping("/all")
    public ResponseEntity<CategoriesResponseDto> GetAllCategories() {
        LOGGER.info("Received all Categories request");
        try {
            CategoriesResponseDto response = categoriesService.getAllCategories();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred", e);
        }
    }
}
