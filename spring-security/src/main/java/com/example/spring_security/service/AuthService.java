package com.example.spring_security.service;

import com.example.spring_security.dto.SignInRequest;
import com.example.spring_security.dto.SignInResponse;
import com.example.spring_security.dto.SignUpRequest;

public interface AuthService {
    void registerUser(SignUpRequest signUpRequest);
    SignInResponse loginUser(SignInRequest signInRequest);
}

