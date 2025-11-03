package com.mbapps.fc.provider.security.payload.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*]).+$",
            message = "Password must contain at least one number and one special character (!@#$%^&*)")
    private String password;

    private Set<String> roles;
}