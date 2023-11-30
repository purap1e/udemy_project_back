package com.example.udemy.services;

import com.example.udemy.entities.Role;

import java.util.UUID;

public interface RoleService {
    Role save(String name);
    Role findByName(String name);
}
