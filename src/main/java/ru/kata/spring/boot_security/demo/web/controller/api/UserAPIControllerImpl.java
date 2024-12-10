package ru.kata.spring.boot_security.demo.web.controller.api;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import ru.kata.spring.boot_security.demo.dal.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.dal.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/api/users/")
public class UserAPIControllerImpl implements UserAPIController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private static final String DEFAULT_PAGE = "redirect:/";
    private static final String CREATE_USER_PAGE = "user/createUser";
    private static final String UPDATE_USER_PAGE = "user/editUser";
    private static final String ERROR_API_PAGE = "redirect:/apiError";

    @Autowired
    public UserAPIControllerImpl(UserRepository userService, RoleRepository roleRepository) {
        this.userRepository = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByID(Long id) {
        return userRepository.getReferenceById(id);
    }


    @Override
    @PostMapping("create")
    public String createUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.User", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return CREATE_USER_PAGE;
        }
        userRepository.save(user);
        return DEFAULT_PAGE;
    }

    @Override
    @PostMapping("update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.User", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return UPDATE_USER_PAGE;
        }
        userRepository.save(user);
        return DEFAULT_PAGE;
    }

    @Override
    @GetMapping(value = "delete")
    public String deleteUser(@RequestParam(defaultValue = "-1") Long id) {
        userRepository.deleteById(id);
        return DEFAULT_PAGE;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
