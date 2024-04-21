package com.everysource.everysource.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String owner;
    @Setter
    @Column(unique = true)
    private String repo;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String content;


}
