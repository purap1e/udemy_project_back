package com.example.udemy.services;

import com.example.udemy.dto.UserLoginRequestDTO;
import com.example.udemy.dto.UserResponseDTO;
import com.example.udemy.exceptions.UsernameExistsException;

import java.util.UUID;

public interface UserService {
    UserResponseDTO getUser(UUID id);
    UUID register(UserLoginRequestDTO userLoginRequestDTO) throws UsernameExistsException;

    UUID addRoleToUser(UUID userId, String roleName);
}
