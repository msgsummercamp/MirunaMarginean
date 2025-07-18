package com.example.spring_boot_rest_api.controller;

import com.example.spring_boot_rest_api.dto.FieldError;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidationErrorResponse {
    private List<FieldError> errors;

    public ValidationErrorResponse(List<FieldError> errors) {
        this.errors = errors;
    }

}

