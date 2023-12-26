package com.example.udemy.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column
    @GeneratedValue
    protected UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = EAGER)
    private Set<Role> roles;

    @ManyToMany(fetch = EAGER)
    private List<Song> song;
}
