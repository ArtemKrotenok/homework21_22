package com.gmail.artemkrotenok.mvc.service.model;

import com.gmail.artemkrotenok.mvc.repository.enums.RoleEnum;

public class UserDTO {

    private String password;
    private String username;
    private RoleEnum role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

}
