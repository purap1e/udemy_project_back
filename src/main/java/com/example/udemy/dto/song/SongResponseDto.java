package com.example.udemy.dto.song;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SongResponseDto {
    private UUID id;
    private String name;
    private String artist;
    private String album;
    private String image;
    private String audio;
    private Date updatedTime;
    private int test;
    private Boolean isFavourite;
}
