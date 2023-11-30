package com.example.udemy.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    @Column
    @GeneratedValue
    protected UUID id;

    @Column(name = "name")
    private String name;
}
