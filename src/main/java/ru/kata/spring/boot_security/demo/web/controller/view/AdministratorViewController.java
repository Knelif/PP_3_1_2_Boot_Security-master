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


@Controller
@RequestMapping("/admin")
public class AdministratorViewController {
    private final UserAPIController userAPI;

    @Autowired
    public AdministratorViewController(UserAPIController userAPI) {
        this.userAPI = userAPI;
    }


    @GetMapping(value = "")
    public String getViewUserListPage(ModelMap modelMap) {
        modelMap.addAttribute("userList", userAPI.getUserList());
        return "admin/allUsers";
    }


    @GetMapping(value = "/createUser")
    public String getCreateUserPage(Model model) {
        if (!model.containsAttribute("user")) model.addAttribute("user", new User());
        model.addAttribute("roles", userAPI.getAllRoles());
        System.out.println(model);
        return "admin/createUser";
    }


    @GetMapping(value = "/editUser")
    public String getEditUserPage(Model model,
                                  @RequestParam(defaultValue = "-1") Long id) {
        if (!model.containsAttribute("user")) model.addAttribute("user", userAPI.getUserByID(id));
        model.addAttribute("roles", userAPI.getAllRoles());
        return "admin/editUser";
    }
}