package com.example.udemy.services;

import com.example.udemy.dto.song.SongDto;
import com.example.udemy.dto.song.SongResponseDto;
import com.example.udemy.entities.Song;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface SongService {
    UUID save(SongDto songDto);
    SongResponseDto get(UUID songId);
    void uploadData(UUID songId, MultipartFile image, MultipartFile audio);
    List<SongResponseDto> getAll();
    List<Song> getAllByIds(List<UUID> ids);
    Song getEntitySong(UUID id);
}
