package com.example.udemy.services;

import com.example.udemy.dto.song.SongResponseDto;

import java.util.List;

public interface ElasticsearchService {
    void createIndex();
    List<SongResponseDto> searchByName(String name);
    void addSongToElastic(SongResponseDto songResponseDto);
}
