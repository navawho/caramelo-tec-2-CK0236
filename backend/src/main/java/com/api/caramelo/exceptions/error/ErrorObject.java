package com.api.caramelo.exceptions.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorObject {

    private String message;
    private String field;
    private Object parameter;
}