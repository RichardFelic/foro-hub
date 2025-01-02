package com.hub.foro.api.modules.shared.validation;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
