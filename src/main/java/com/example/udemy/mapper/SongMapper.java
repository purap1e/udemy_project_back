package com.example.udemy.mapper;

import com.example.udemy.dto.song.SongDto;
import com.example.udemy.entities.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper extends Mappable<Song, SongDto> {
}
