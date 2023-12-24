package com.example.udemy.dto.playlist;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PlaylistResponseDto {
    private UUID id;
    private String name;
    private String image;
}
