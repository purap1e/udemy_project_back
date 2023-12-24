package com.example.udemy.controllers;

import com.example.udemy.dto.user.UserLoginRequestDTO;
import com.example.udemy.dto.user.UserResponseDTO;
import com.example.udemy.exceptions.UsernameExistsException;
import com.example.udemy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public UUID signUpNewUser(@RequestBody UserLoginRequestDTO userLoginRequestDTO) throws UsernameExistsException {
        return userService.register(userLoginRequestDTO);
    }

    @PostMapping("/add-role-to-user")
    public UUID addRoleToUser(@RequestParam UUID userId,
                              @RequestParam String roleName) {
        return userService.addRoleToUser(userId, roleName);
    }

    @GetMapping("/{userId}")
    public UserResponseDTO get(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }
}
