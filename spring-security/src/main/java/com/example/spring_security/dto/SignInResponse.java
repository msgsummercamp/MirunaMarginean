package com.example.spring_security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class SignInResponse {
    private String token;
    private Set<String> roles;
}
