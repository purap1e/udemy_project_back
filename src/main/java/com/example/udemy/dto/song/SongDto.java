package com.example.udemy.dto.song;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class SongDto {
    private String name;
    private String artist;
    private String album;
}
