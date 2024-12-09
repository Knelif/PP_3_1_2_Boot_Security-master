package ru.kata.spring.boot_security.demo.web.controller.view;


import org.springframework.ui.ModelMap;
import ru.kata.spring.boot_security.demo.dal.model.User;

public interface UserViewController {
    String getViewUserListPage(ModelMap model);

    String getCreateUserPage(User user);

    String getEditUserPage(ModelMap model, Long Id);
}
