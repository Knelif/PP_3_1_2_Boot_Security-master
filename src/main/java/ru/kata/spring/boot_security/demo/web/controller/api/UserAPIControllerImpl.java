package ru.kata.spring.boot_security.demo.web.controller.api;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.dal.model.Role;
import ru.kata.spring.boot_security.demo.dal.model.User;
import ru.kata.spring.boot_security.demo.dal.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.dal.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/api/users/")
public class UserAPIControllerImpl implements UserAPIController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private static final String DEFAULT_PAGE = "redirect:/";
    private static final String CREATE_USER_PAGE = "redirect:/admin/createUser";
    private static final String UPDATE_USER_PAGE = "redirect:/admin/editUser";
    private static final String ERROR_API_PAGE = "redirect:/apiError";

    @Autowired
    public UserAPIControllerImpl(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @Override
    public User getUserByID(Long id) {
        return userService.getUserById(id);
    }


    @Override
    @PostMapping("create")
    public String createUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return CREATE_USER_PAGE;
        }
        userService.saveUser(user);
        return DEFAULT_PAGE;
    }

    @Override
    @PostMapping("update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return String.format("%s?id=%d", UPDATE_USER_PAGE, user.getId());
        }
        userService.updateUser(user);
        return DEFAULT_PAGE;
    }

    @Override
    @GetMapping(value = "delete")
    public String deleteUser(@RequestParam(defaultValue = "-1") Long id) {
        userService.deleteUserById(id);
        return DEFAULT_PAGE;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
