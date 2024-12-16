package ru.kata.spring.boot_security.demo.dal.validator;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.dal.model.User;
import ru.kata.spring.boot_security.demo.dal.service.UserService;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User targetUser = (User) target;
        User persistUser =null;
        try {
            persistUser = userService.getUserByEmail(targetUser.getEmail());
        } catch (EntityNotFoundException | NoResultException enf){
            return;
        }
        if (persistUser == null) return;
        if (persistUser.getId().equals(targetUser.getId())) return;
        errors.rejectValue("email", "", "Another user already have this email");
    }
}
