package com.example.spring_security.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UserPatchRequest {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,15}$", message = "Username must be 3-15 alphanumeric characters")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email must be a valid email address")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Size(max = 255, message = "First name must be at most 50 characters")
    private String firstname;

    @Size(max = 255, message = "Last name must be at most 50 characters")
    private String lastname;

    private Set<String> roles;
}
