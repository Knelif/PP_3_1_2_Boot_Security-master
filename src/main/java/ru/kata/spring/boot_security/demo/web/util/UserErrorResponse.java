package ru.kata.spring.boot_security.demo.web.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserErrorResponse {
    private String message;
    private Long timeStamp;
}
