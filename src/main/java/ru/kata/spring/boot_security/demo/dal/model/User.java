package ru.kata.spring.boot_security.demo.dal.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
@NoArgsConstructor
@Getter @Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = 2, max = 30, message = "Min length is 2, Max length is 30")
    private String name;

    @NotEmpty(message = "Lastname can't be empty")
    @Size(min = 2, max = 30, message = "Min length is 2, Max length is 30")
    private String lastname;

    @NotEmpty
    @Email(message = "Wrong email")
    private String email;

    // TODO: Entity attributes for age
    private Byte age;
    // TODO: Entity attributes for password
    private String password;

    // TODO: Entity attributes for roles
    private List<Role> roles;


    public User(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(lastname, user.lastname) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, email);
    }


}
