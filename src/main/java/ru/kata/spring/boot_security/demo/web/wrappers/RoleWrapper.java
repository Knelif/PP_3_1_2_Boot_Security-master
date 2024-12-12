package ru.kata.spring.boot_security.demo.web.wrappers;

import ru.kata.spring.boot_security.demo.dal.model.Role;

public class RoleWrapper {
    private final Role role;

    private RoleWrapper(Role role) {
        this.role = role;
    }
    public static RoleWrapper of(Role role){
        return new RoleWrapper(role);
    }
    public Long getId(){
        return role.getId();
    }
    public String getName(){
        return role.getName().replace("ROLE_","");
    }


}
