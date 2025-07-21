package com.example.spring_boot_rest_api.controller;

import com.example.spring_boot_rest_api.dto.FieldError;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {
    private List<FieldError> errors;
}

