package com.example.udemy.services;

import com.example.udemy.entities.Role;
import com.example.udemy.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    User registerUser(User user) throws Exception;
    List<User> getUsers();
}
