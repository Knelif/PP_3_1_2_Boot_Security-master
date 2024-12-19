package ru.kata.spring.boot_security.demo.dal.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
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
        TypedQuery<User> query = entityManager.createQuery("FROM User u JOIN FETCH u.role WHERE u.id = :id", User.class);
        query.setParameter("id", id);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException nre) {
            throw new EntityNotFoundException(String.format("User with id %s not found", id),nre);
        }
        entityManager.detach(user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws EntityNotFoundException {
        TypedQuery<User> query = entityManager.createQuery("FROM User u JOIN FETCH u.role WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException nre) {
            throw new EntityNotFoundException(String.format("User with email %s not found", email),nre);
        }
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
        return entityManager.createQuery("FROM User u JOIN FETCH u.role order by u.id", User.class)
                .getResultList();
    }

    @Override
    public void deleteUserById(Long id) throws EntityNotFoundException {
        User user = entityManager.find(User.class, id);
        if (user == null) throw new EntityNotFoundException(String.format("User with id %d is not found", id));
        entityManager.remove(user);
    }


}
