package ru.kata.spring.boot_security.demo.dal.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dal.dao.UserDAO;
import ru.kata.spring.boot_security.demo.dal.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public void saveUser(User newUser) {
        userDAO.saveUser(newUser);
    }

    @Transactional
    @Override
    public void updateUser(User newUser) {
        userDAO.updateUser(newUser);
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUserList() {
        return userDAO.getUserList();
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userDAO.deleteUserById(id);
    }
}
