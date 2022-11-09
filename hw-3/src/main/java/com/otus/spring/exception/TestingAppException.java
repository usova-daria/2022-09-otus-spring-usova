package com.otus.spring.exception;

public class TestingAppException extends RuntimeException {

    public TestingAppException() {
        super();
    }

    public TestingAppException(Throwable cause) {
        super(cause);
    }

    public TestingAppException(String message) {
        super(message);
    }

    public TestingAppException(String message, Throwable cause) {
        super(message, cause);
    }

}
