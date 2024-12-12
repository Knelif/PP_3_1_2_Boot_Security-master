package ru.kata.spring.boot_security.demo.web.controller.api;


import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.dal.model.Role;
import ru.kata.spring.boot_security.demo.dal.model.User;

import java.util.List;

public interface UserAPIController {
    String createUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    List<User> getUserList();

    User getUserByID(Long id);

    User getUserByEmail(String email);

    String updateUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String deleteUser(Long id);

    List<Role> getAllRoles();
}
