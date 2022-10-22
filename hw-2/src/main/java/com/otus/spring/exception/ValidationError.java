package com.otus.spring.exception;

public class ValidationError extends TestingAppException {

    public ValidationError() {
        super();
    }

    public ValidationError(String message) {
        super(message);
    }

    public ValidationError(String message, Throwable cause) {
        super(message, cause);
    }

}
