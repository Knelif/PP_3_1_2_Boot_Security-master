package ru.kata.spring.boot_security.demo.dal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dal.model.Role;
import ru.kata.spring.boot_security.demo.dal.model.User;
@Repository
// TODO: USER JPA repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
