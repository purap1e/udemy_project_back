package com.example.udemy.controllers;

import com.example.udemy.entities.User;
import com.example.udemy.repositories.RoleRepository;
import com.example.udemy.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpNewUser(@RequestParam(name = "user") String userJson) throws Exception {
        User user = new ObjectMapper().readValue(userJson,User.class);
        user.getRoles().add(roleRepository.findByName("ROLE_USER"));
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
