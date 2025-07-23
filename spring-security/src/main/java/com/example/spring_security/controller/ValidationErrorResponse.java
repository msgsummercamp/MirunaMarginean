package com.example.spring_security.controller;

import com.example.spring_security.dto.FieldError;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {
    private List<FieldError> errors;
}

