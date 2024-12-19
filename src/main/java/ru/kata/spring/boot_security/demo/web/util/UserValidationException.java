package ru.kata.spring.boot_security.demo.web.util;

import java.util.List;
import java.util.Map;

public class UserValidationException extends RuntimeException{
    private final Map<String, List<String>> errors;
    public UserValidationException(String message, Map<String, List<String>> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
