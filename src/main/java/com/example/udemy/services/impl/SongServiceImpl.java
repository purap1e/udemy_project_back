package com.example.udemy.services.impl;

import com.example.udemy.dto.song.SongDto;
import com.example.udemy.dto.song.SongResponseDto;
import com.example.udemy.entities.Song;
import com.example.udemy.exceptions.NotFoundException;
import com.example.udemy.mapper.SongMapper;
import com.example.udemy.mapper.SongResponseMapper;
import com.example.udemy.repositories.SongRepository;
import com.example.udemy.services.ElasticsearchService;
import com.example.udemy.services.SongService;
import com.example.udemy.services.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongServiceImpl implements SongService {

    private final SongMapper songMapper;
    private final SongRepository songRepository;
    private final SongResponseMapper songResponseMapper;
    private final StorageService storageService;
    private final ElasticsearchService elasticsearchService;

    @Override
    public UUID save(SongDto songDto) {
        Song song = songMapper.toEntity(songDto);
        return songRepository.save(song).getId();
    }

    @Override
    public SongResponseDto get(UUID songId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new NotFoundException("song not found"));
        return songResponseMapper.apply(song);
    }

    @Override
    public void uploadData(UUID songId, MultipartFile image, MultipartFile audio) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new NotFoundException("song not found"));
        String imageFileName = storageService.upload(image);
        String audioFileName = storageService.upload(audio);
        song.setImage(imageFileName);
        song.setAudio(audioFileName);

        SongResponseDto songResponseDto = songResponseMapper.apply(song);
        elasticsearchService.addSongToElastic(songResponseDto);

        songRepository.save(song);
    }

    @Override
    public List<SongResponseDto> getAll() {
        return songRepository.findAll().stream().map(songResponseMapper).collect(Collectors.toList());
    }

    @Override
    public List<Song> getAllByIds(List<UUID> ids) {
        return songRepository.findAllByIdIn(ids);
    }

    @Override
    public Song getEntitySong(UUID id) {
        return songRepository.findById(id).orElseThrow(() -> new NotFoundException("song not found"));
    }

    @Override
    public List<SongResponseDto> getAllBySearching(String name) {
        return elasticsearchService.searchByName(name);
    }
}
