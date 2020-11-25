package com.api.caramelo.exceptions.error;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private final String message;
    private final List<ErrorObject> errors;
}
