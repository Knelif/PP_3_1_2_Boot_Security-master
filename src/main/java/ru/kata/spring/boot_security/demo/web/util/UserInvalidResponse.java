package ru.kata.spring.boot_security.demo.web.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
public class UserInvalidResponse {
    private String message;
    private Map<String, List<String>> errors;
}
