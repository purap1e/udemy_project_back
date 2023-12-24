package com.example.udemy.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @Column
    @GeneratedValue
    protected UUID id;

    @Column
    private String image;

    @Column
    private String name;

    @ManyToMany
    private List<Song> song;
}
