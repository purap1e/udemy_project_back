package com.example.udemy.controllers;

import com.example.udemy.entities.Role;
import com.example.udemy.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public Role save(@RequestParam String name) {
        return roleService.save(name);
    }
}
