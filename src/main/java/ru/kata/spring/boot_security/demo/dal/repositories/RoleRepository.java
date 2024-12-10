package ru.kata.spring.boot_security.demo.dal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.dal.model.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
