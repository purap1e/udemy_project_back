package com.example.udemy.controllers;

import com.example.udemy.dto.ImagesResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImgController {
    @GetMapping
    public ImagesResponseDTO get() {
        List<String> images = List.of("dance_the_night.jpg","die_for_you.jpg","fujii_kaze.jpg","get_lucky.jpg");
        return ImagesResponseDTO.builder().images(images).build();
    }
}
