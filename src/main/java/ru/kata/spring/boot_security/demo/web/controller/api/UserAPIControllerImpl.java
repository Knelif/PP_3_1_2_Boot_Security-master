package ru.kata.spring.boot_security.demo.web.controller.api;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dal.model.Role;
import ru.kata.spring.boot_security.demo.dal.model.User;
import ru.kata.spring.boot_security.demo.dal.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.dal.service.UserService;
import ru.kata.spring.boot_security.demo.dal.validator.UserValidator;
import ru.kata.spring.boot_security.demo.web.dto.UserDTO;
import ru.kata.spring.boot_security.demo.web.util.UserErrorResponse;
import ru.kata.spring.boot_security.demo.web.util.UserInvalidResponse;
import ru.kata.spring.boot_security.demo.web.util.UserValidationException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/")
public class UserAPIControllerImpl implements UserAPIController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserValidator userValidator;

    @Autowired
    public UserAPIControllerImpl(UserService userService, RoleRepository roleRepository, UserValidator userValidator) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userValidator = userValidator;
    }

    @GetMapping
    @Override
    public List<UserDTO> getUserList() {
        return userService.getUserList().stream().map(UserDTO::of).collect(Collectors.toList());
    }

    @Override
    @GetMapping("{id}")
    public UserDTO getUserByID(@PathVariable Long id) {
        return UserDTO.of(userService.getUserById(id));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return UserDTO.of(userService.getUserByEmail(email));
    }

    @Override
    @PostMapping("create")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        User user = UserDTO.getUser(userDTO);
        isValidUser(user, bindingResult);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @PutMapping("update/{id}")
    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        User user = UserDTO.getUser(userDTO);
        isValidUser(user, bindingResult);
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping("roles")
    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }
    private boolean isValidUser(User user, BindingResult bindingResult) throws UserValidationException {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            Map<String,List<String>> errorMap =
                    bindingResult.getFieldErrors()
                            .stream()
                            .collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
            throw new UserValidationException("Ivalid fields",errorMap);
        }
        return true;
    }
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(EntityNotFoundException enf) {
        return new ResponseEntity<>(new UserErrorResponse(enf.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<UserInvalidResponse> handleException(UserValidationException uve) {
        return new ResponseEntity<>(new UserInvalidResponse(uve.getMessage(), uve.getErrors()), HttpStatus.BAD_REQUEST);
    }



}
