package com.mbapps.fc.provider.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource; // Import this
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc; // Keep if you use other WebMvc features

import java.util.Arrays;
import java.util.List; // Import List

@Configuration
@EnableWebMvc // Keep this if you have other Spring MVC configurations, otherwise it might be optional
public class WebConfig {

    // This bean will be picked up by Spring Security's CORS integration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of( // Use List.of for an immutable list
                "http://localhost:5174",
                "https://thecozycookbookwebui.vercel.app"
        ));
        configuration.setAllowCredentials(true); // Allow cookies, authorization headers, etc.
        configuration.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name() // IMPORTANT: Always allow OPTIONS for preflight requests
        ));
        configuration.setMaxAge(7200L); // Cache preflight response for 2 hours

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply to all paths
        return source;
    }
}