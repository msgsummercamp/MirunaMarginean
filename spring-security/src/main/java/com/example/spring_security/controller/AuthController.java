package com.example.spring_security.controller;

import com.example.spring_security.dto.SignInRequest;
import com.example.spring_security.dto.SignInResponse;
import com.example.spring_security.dto.SignUpRequest;
import com.example.spring_security.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.registerUser(signUpRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {
        SignInResponse response = authService.loginUser(signInRequest);
        return ResponseEntity.ok(response);
    }
}
