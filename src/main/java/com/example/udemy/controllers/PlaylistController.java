package com.example.udemy.controllers;

import com.example.udemy.dto.playlist.PlaylistResponseDto;
import com.example.udemy.dto.song.SongResponseDto;
import com.example.udemy.services.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public UUID save(@RequestParam String name,
                     @RequestParam MultipartFile image) {
        return playlistService.save(name, image);
    }

    @GetMapping
    public List<PlaylistResponseDto> getAll() {
        return playlistService.getAll();
    }

    @GetMapping("/{id}")
    public PlaylistResponseDto get(@PathVariable UUID id) {
        return playlistService.get(id);
    }

    @GetMapping("/{id}/songs")
    public List<SongResponseDto> getAllSongsOfPlaylist(@PathVariable UUID id) {
        return playlistService.getAllSongsByPlaylist(id);
    }

    @PostMapping("/add-songs/{id}")
    public UUID addSongs(@PathVariable UUID id,
                         @RequestBody Map<String, List<UUID>> request) {
        return playlistService.addSongs(id, request.get("ids"));
    }

}
