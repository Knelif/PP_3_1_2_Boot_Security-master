package ru.kata.spring.boot_security.demo.dal.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.dal.model.User;

import java.util.List;

@Repository
@Primary
public class UserDAOHibernateImpl implements UserDAO {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        User user = entityManager.find(User.class, id);
        if (user == null) throw new EntityNotFoundException(String.format("User with id %d not found", id));
        entityManager.detach(user);
        return user;
    }

    @Override
    public User getUserByEmail(String Email) throws EntityNotFoundException {
        TypedQuery<User> query = entityManager.createQuery("FROM User WHERE email = :email", User.class);
        query.setParameter("email", Email);
        User user = query.getSingleResult();
        if (user == null) throw new EntityNotFoundException(String.format("User with email %s not found", Email));
        entityManager.detach(user);
        return user;
    }

    @Override
    public void updateUser(User user) throws EntityNotFoundException {
        getUserById(user.getId());
        entityManager.merge(user);
    }


    @Override
    public List<User> getUserList() {
        return entityManager.createQuery("FROM User", User.class)
                .getResultList();
    }

    @Override
    public void deleteUserById(Long id) throws EntityNotFoundException {
        User user = entityManager.find(User.class, id);
        if (user == null) throw new EntityNotFoundException(String.format("User with id %d is not found", id));
        entityManager.remove(user);
    }


}
