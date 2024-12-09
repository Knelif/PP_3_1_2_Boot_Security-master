package ru.kata.spring.boot_security.demo.dal.service;


import jakarta.persistence.EntityNotFoundException;
import ru.kata.spring.boot_security.demo.dal.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    void updateUser(User user) throws EntityNotFoundException;

    User getUserById(Long id) throws EntityNotFoundException;

    List<User> getUserList();

    void deleteUserById(Long id) throws EntityNotFoundException;
}
