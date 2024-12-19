package ru.kata.spring.boot_security.demo.web.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.dal.model.Role;
import ru.kata.spring.boot_security.demo.web.dto.UserDTO;

import java.util.List;
import java.util.Set;

public interface UserAPIController {
    List<UserDTO> getUserList();

    UserDTO getUserByID(Long id);

    UserDTO getUserByEmail(String email);

    ResponseEntity<HttpStatus>  createUser(UserDTO user, BindingResult bindingResult);

    ResponseEntity<HttpStatus> updateUser(UserDTO user, BindingResult bindingResult);

    ResponseEntity<HttpStatus> deleteUser(Long id);

    Set<Role> getAllRoles();
}
