package ru.kata.spring.boot_security.demo.web.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.dal.model.User;
import ru.kata.spring.boot_security.demo.web.controller.api.UserAPIController;
import ru.kata.spring.boot_security.demo.web.decorators.UserWrapper;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserViewController {
    private final UserAPIController userAPI;

    @Autowired
    public UserViewController(UserAPIController userAPI) {
        this.userAPI = userAPI;
    }

    @GetMapping
    public String getAboutUserPage(ModelMap modelMap, Principal principal) {
        User user = userAPI.getUserByEmail(principal.getName());
        modelMap.addAttribute("currentUser", UserWrapper.of(user));
        return "user/aboutUser";
    }
}
