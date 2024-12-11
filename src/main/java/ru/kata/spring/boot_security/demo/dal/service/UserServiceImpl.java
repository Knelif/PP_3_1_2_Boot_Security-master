package ru.kata.spring.boot_security.demo.dal.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dal.dao.UserDAO;
import ru.kata.spring.boot_security.demo.dal.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        encodePassword(user);
        userDAO.saveUser(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        encodePassword(user);
        userDAO.updateUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public User getUserByEmail(String Email) throws EntityNotFoundException {
        return userDAO.getUserByEmail(Email);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.getUserByEmail(username);
    }

    @Autowired
    @Lazy
    public void setPasswordEncoder(PasswordEncoder pe) {
        passwordEncoder = pe;
    }

    private User encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
