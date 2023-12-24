package com.example.udemy.services;

import com.example.udemy.dto.playlist.PlaylistResponseDto;
import com.example.udemy.dto.song.SongResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PlaylistService {
    List<PlaylistResponseDto> getAll();
    UUID save(String name, MultipartFile image);
    List<SongResponseDto> getAllSongsByPlaylist(UUID playlistId);
    UUID addSongs(UUID playlistId, List<UUID> ids);

    PlaylistResponseDto get(UUID id);
}
