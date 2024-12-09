package ru.kata.spring.boot_security.demo.web.controller.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dal.model.User;
import ru.kata.spring.boot_security.demo.web.controller.api.UserAPIController;


@Controller
public class UserViewControllerImpl implements UserViewController {
    private final UserAPIController userAPI;

    @Autowired
    public UserViewControllerImpl(UserAPIController userAPI) {
        this.userAPI = userAPI;
    }

    @Override
    @GetMapping(value = "userList")
    public String getViewUserListPage(ModelMap modelMap) {
        modelMap.addAttribute("userList", userAPI.getUserList());
        return "user/allUsers";
    }

    @Override
    @GetMapping(value = "createUser")
    public String getCreateUserPage(@ModelAttribute("user") User user) {
        if (user == null) user = new User();
        return "user/createUser";
    }

    @Override
    @GetMapping(value = "editUser")
    public String getEditUserPage(ModelMap modelMap, @RequestParam(defaultValue = "-1") Long id) {
        if (!modelMap.containsAttribute("user")) modelMap.addAttribute("user", userAPI.getUserByID(id));
        return "user/editUser";
    }
}
