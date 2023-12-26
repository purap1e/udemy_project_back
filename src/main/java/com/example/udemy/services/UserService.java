package com.example.udemy.services;

import com.example.udemy.dto.user.UserLoginRequestDTO;
import com.example.udemy.entities.User;
import com.example.udemy.exceptions.UsernameExistsException;

import java.util.UUID;

public interface UserService {
    User getUser(UUID id);
    UUID register(UserLoginRequestDTO userLoginRequestDTO) throws UsernameExistsException;
    UUID addRoleToUser(UUID userId, String roleName);
    void addSongToUser(UUID userId, UUID songId);
    void deleteSongFromUser(UUID userId, UUID songId);
}
