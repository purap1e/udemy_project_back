package com.example.udemy.controllers;

import com.example.udemy.dto.song.SongDto;
import com.example.udemy.dto.song.SongResponseDto;
import com.example.udemy.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/songs")
public class SongController {

    private final SongService songService;

    @PostMapping
    public UUID save(@RequestBody SongDto songDto) {
        return songService.save(songDto);
    }

    @PostMapping("/{id}")
    public void upload(@PathVariable UUID id,
                       @RequestParam MultipartFile image,
                       @RequestParam MultipartFile audio) {
        songService.uploadData(id, image, audio);
    }

    @GetMapping
    public List<SongResponseDto> getAll() {
        return songService.getAll();
    }
}