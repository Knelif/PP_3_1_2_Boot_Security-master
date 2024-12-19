package ru.kata.spring.boot_security.demo.web.controller.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dal.model.User;
import ru.kata.spring.boot_security.demo.web.controller.api.UserAPIController;
import ru.kata.spring.boot_security.demo.web.dto.UserDTO;

import java.security.Principal;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdministratorViewController {
    private final UserAPIController userAPI;

    @Autowired
    public AdministratorViewController(UserAPIController userAPI) {
        this.userAPI = userAPI;
    }


    @GetMapping(value = "")
    public String getViewUserListPage(ModelMap modelMap, Principal principal) {
        UserDTO userDTO = userAPI.getUserByEmail(principal.getName());
        modelMap.addAttribute("currentUser", userDTO);
        modelMap.addAttribute("userList", userAPI.getUserList());
        if (!modelMap.containsAttribute("user")) modelMap.addAttribute("user", new UserDTO());
        modelMap.addAttribute("roles", userAPI.getAllRoles());
        return "admin/adminPage.html";
    }
}
