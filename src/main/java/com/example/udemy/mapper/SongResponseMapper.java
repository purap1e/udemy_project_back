package com.example.udemy.mapper;

import com.example.udemy.dto.song.SongResponseDto;
import com.example.udemy.entities.Song;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SongResponseMapper implements Function<Song, SongResponseDto> {
    @Override
    public SongResponseDto apply(Song song) {
        return SongResponseDto.builder()
                .name(song.getName())
                .album(song.getAlbum())
                .artist(song.getArtist())
                .image("http://localhost:9000/data/" + song.getImage())
                .audio("http://localhost:9000/data/" + song.getAudio())
                .updatedTime(song.getUpdatedTime())
                .isFavourite(false)
                .build();
    }
}
