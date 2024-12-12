package ru.kata.spring.boot_security.demo.web.wrappers;

import ru.kata.spring.boot_security.demo.dal.model.Role;
import ru.kata.spring.boot_security.demo.dal.model.User;

import java.util.stream.Collectors;

public class UserWrapper {
    private final User user;
    private String role;

    private UserWrapper(User user) {
        this.user = user;
        role = user.getRole().stream().map(Role::toString).collect(Collectors.joining(" "));
    }

    public static UserWrapper of(User user) {
        return new UserWrapper(user);
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
