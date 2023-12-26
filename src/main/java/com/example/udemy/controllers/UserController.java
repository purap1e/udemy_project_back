package com.example.udemy.controllers;

import com.example.udemy.dto.song.SongResponseDto;
import com.example.udemy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/add-role")
    public UUID addRoleToUser(@RequestParam UUID userId,
                              @RequestParam String roleName) {
        return userService.addRoleToUser(userId, roleName);
    }

    @PostMapping("/add-song")
    public void addSong(@RequestParam UUID userId,
                        @RequestParam UUID songId) {
        userService.addSongToUser(userId, songId);
    }

    @DeleteMapping
    public void deleteSong(@RequestParam UUID userId,
                           @RequestParam UUID songId) {
        userService.deleteSongFromUser(userId, songId);
    }

    @GetMapping("/{userId}/songs")
    public List<SongResponseDto> getAllFavourites(@PathVariable UUID userId) {
        return userService.getAllFavourites(userId);
    }

}
