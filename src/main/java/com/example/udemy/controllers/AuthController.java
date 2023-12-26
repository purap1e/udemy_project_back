package com.example.udemy.controllers;

import com.example.udemy.dto.user.UserLoginRequestDTO;
import com.example.udemy.exceptions.UsernameExistsException;
import com.example.udemy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public UUID signUpNewUser(@RequestBody UserLoginRequestDTO userLoginRequestDTO) throws UsernameExistsException {
        return userService.register(userLoginRequestDTO);
    }
}
