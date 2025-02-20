package com.vortexBird.moviePlatform.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Movies",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title", "director"})
)
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovie;

    @Column(nullable = false)
    private String title;

    private String director;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movies_categories",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    private Timestamp releaseDate;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean available;

    private String fileNameImg;
}
