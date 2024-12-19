package ru.kata.spring.boot_security.demo.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kata.spring.boot_security.demo.dal.model.Role;
import ru.kata.spring.boot_security.demo.dal.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Lastname can't be empty")
    @Size(min = 2, max = 30, message = "Lastname must be between 2 and 30 characters")
    private String lastname;

    private Byte age;

    @NotEmpty(message = "Email using as login and can't be empty")
    @Email(message = "Wrong email")
    private String email;

    @NotEmpty(message = "Password can't be empty")
    @Size(min = 2, message = "Password must contain more than 2 characters")
    private String password;

    @NotEmpty(message = "Select one or more role")
    private Set<Role> role;


    private UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        lastname = user.getLastname();
        age = user.getAge();
        email = user.getEmail();
        password = user.getPassword();

        role = user.getRole() != null ? user.getRole() : new HashSet<>();
    }

    public static UserDTO of(User user) {
        return new UserDTO(user);
    }


    public String getInlineRoles() {
        return role.stream().map(Role::toString).collect(Collectors.joining(" "));
    }

    public static User getUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;
    }


}
