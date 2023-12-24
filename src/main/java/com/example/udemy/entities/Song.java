package com.example.udemy.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @Column
    @GeneratedValue
    protected UUID id;

    @Column
    private String name;

    @Column
    private String artist;

    @Column
    private String album;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;

    @Column
    private String image;

    @Column
    private String audio;
}
