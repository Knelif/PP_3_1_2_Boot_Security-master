package ru.kata.spring.boot_security.demo.web.decorators;

import ru.kata.spring.boot_security.demo.dal.model.User;

import java.util.stream.Collectors;

public class UserDecorator {
    private final User user;
    private String role;

    private UserDecorator(User user) {
        this.user = user;
        role = user.getRole().stream().map(role -> role.getName().replace("ROLE_", "")).collect(Collectors.joining(" "));
    }

    public static UserDecorator of(User user) {
        return new UserDecorator(user);
    }
    public Long getId() {
        return user.getId();
    }
    public String getName() {
        return user.getName();
    }

    public String getLastname() {
        return user.getLastname();
    }

    public Byte getAge() {
        return user.getAge();
    }
    public String getEmail() {
        return user.getEmail();
    }
    public String getRole() {
        return role;
    }
}
