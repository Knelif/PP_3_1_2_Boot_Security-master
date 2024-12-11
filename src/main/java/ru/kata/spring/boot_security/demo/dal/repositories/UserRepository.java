package ru.kata.spring.boot_security.demo.dal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.dal.model.User;
@Repository
// TODO: USER JPA repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
