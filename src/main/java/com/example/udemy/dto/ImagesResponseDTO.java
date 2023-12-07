package com.example.udemy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ImagesResponseDTO {
    private List<String> images;
}
