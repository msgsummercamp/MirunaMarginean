package com.example.spring_security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private Set<String> roles;
}