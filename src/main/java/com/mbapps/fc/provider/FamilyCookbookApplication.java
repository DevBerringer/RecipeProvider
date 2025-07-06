package com.mbapps.fc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FamilyCookbookApplication {
    public static void main(String[] args) {
        System.out.println("APP_COOKIE_SECURE: " + System.getenv("APP_COOKIE_SECURE"));
        SpringApplication.run(FamilyCookbookApplication.class, args);
    }
}
