package ru.kata.spring.boot_security.demo.web.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.dal.model.User;
import ru.kata.spring.boot_security.demo.dal.repositories.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserViewController {
    private final UserRepository userRepository;

    @Autowired
    public UserViewController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getAboutUserPage(ModelMap modelMap, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        modelMap.addAttribute("user", user);
        return "user/aboutUser";
    }
}
