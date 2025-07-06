package com.mbapps.fc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FamilyCookbookApplication {
    public static void main(String[] args) {
        System.getenv().forEach((k, v) -> System.out.println(k + "=" + v));
        SpringApplication.run(FamilyCookbookApplication.class, args);
    }
}
