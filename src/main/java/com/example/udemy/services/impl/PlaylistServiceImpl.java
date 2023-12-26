package com.example.udemy.services.impl;

import com.example.udemy.dto.playlist.PlaylistResponseDto;
import com.example.udemy.dto.song.SongResponseDto;
import com.example.udemy.entities.Playlist;
import com.example.udemy.entities.Song;
import com.example.udemy.entities.User;
import com.example.udemy.mapper.SongResponseMapper;
import com.example.udemy.repositories.PlaylistRepository;
import com.example.udemy.services.PlaylistService;
import com.example.udemy.services.SongService;
import com.example.udemy.services.StorageService;
import com.example.udemy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private static final String MINIO_URL_FOR_FETCH_DATA = "http://localhost:9000/data/";

    private final PlaylistRepository playlistRepository;
    private final SongResponseMapper songResponseMapper;
    private final StorageService storageService;
    private final SongService songService;
    private final UserService userService;

    @Override
    public List<PlaylistResponseDto> getAll() {
        return playlistRepository.findAll()
                .stream()
                .map(playlist -> PlaylistResponseDto.builder()
                        .id(playlist.getId())
                        .image(MINIO_URL_FOR_FETCH_DATA + playlist.getImage())
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
    public List<SongResponseDto> getAllSongsByPlaylist(UUID userId, UUID playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("playlist not found"));
        List<Song> songs = playlist.getSong();
        songs.sort(Comparator.comparing(Song::getUpdatedTime));

        User user = userService.getUser(userId);
        List<SongResponseDto> songResponseDtoList = new ArrayList<>();

        songs.forEach(song -> {
            SongResponseDto songResponseDto = songResponseMapper.apply(song);
            if (user.getSong().contains(song)) {
                songResponseDto.setTest(1);
                songResponseDto.setIsFavourite(true);
            }
            songResponseDtoList.add(songResponseDto);
        });

        return songResponseDtoList;
    }

    @Override
    public PlaylistResponseDto get(UUID id) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new RuntimeException("playlist not found"));
        return PlaylistResponseDto.builder()
                .id(id)
                .name(playlist.getName())
                .image(MINIO_URL_FOR_FETCH_DATA + playlist.getImage())
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
