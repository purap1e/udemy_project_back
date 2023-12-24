package com.example.udemy.services.impl;

import com.example.udemy.dto.playlist.PlaylistResponseDto;
import com.example.udemy.dto.song.SongResponseDto;
import com.example.udemy.entities.Playlist;
import com.example.udemy.entities.Song;
import com.example.udemy.mapper.SongResponseMapper;
import com.example.udemy.repositories.PlaylistRepository;
import com.example.udemy.services.PlaylistService;
import com.example.udemy.services.SongService;
import com.example.udemy.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongResponseMapper songResponseMapper;
    private final StorageService storageService;
    private final SongService songService;

    @Override
    public List<PlaylistResponseDto> getAll() {
        return playlistRepository.findAll()
                .stream()
                .map(playlist -> PlaylistResponseDto.builder()
                        .id(playlist.getId())
                        .image("http://localhost:9000/data/" + playlist.getImage())
                        .name(playlist.getName())
                        .build()).toList();
    }

    @Override
    public UUID save(String name, MultipartFile image) {
        Playlist playlist = new Playlist();
        String imageFileName = storageService.upload(image);
        playlist.setName(name);
        playlist.setImage(imageFileName);
        return playlistRepository.save(playlist).getId();
    }

    @Override
    public List<SongResponseDto> getAllSongsByPlaylist(UUID playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("playlist not found"));
        List<Song> songs = playlist.getSong();
        songs.sort(Comparator.comparing(Song::getUpdatedTime));
        return songs.stream().map(songResponseMapper).toList();
    }

    @Override
    public PlaylistResponseDto get(UUID id) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new RuntimeException("playlist not found"));
        return PlaylistResponseDto.builder()
                .id(id)
                .name(playlist.getName())
                .image("http://localhost:9000/data/" + playlist.getImage())
                .build();
    }

    @Override
    public UUID addSongs(UUID playlistId, List<UUID> ids) {
        List<Song> songs = songService.getAllByIds(ids);
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("playlist not found"));
        playlist.setSong(songs);
        return playlistRepository.save(playlist).getId();
    }
}
