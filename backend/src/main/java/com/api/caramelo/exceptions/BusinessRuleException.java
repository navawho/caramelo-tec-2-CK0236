package com.api.caramelo.exceptions;

import com.api.caramelo.exceptions.error.ErrorObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BusinessRuleException extends RuntimeException {
    private List<ErrorObject> errors;

    public void addError(ErrorObject error) {
        this.errors.add(error);
    }

    public boolean checkHasSomeError() {
        return !errors.isEmpty();
    }

    public BusinessRuleException(String message) {
        super(message);
        this.errors = new ArrayList<>();
    }
}
