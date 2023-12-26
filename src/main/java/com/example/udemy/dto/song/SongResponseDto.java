package com.example.udemy.dto.song;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class SongResponseDto {
    private String name;
    private String artist;
    private String album;
    private String image;
    private String audio;
    private Date updatedTime;
    private Boolean isFavourite;
}
