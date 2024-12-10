package ru.kata.spring.boot_security.demo.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.web.controller.view.AdministratorViewController;

@Controller
public class MainController {
    private final AdministratorViewController userView;

    @Autowired
    public MainController(AdministratorViewController userView) {
        this.userView = userView;
    }

    @GetMapping(value = "/")
    public String getHomePage(ModelMap modelMap) {
        return userView.getViewUserListPage(modelMap);
    }
}
