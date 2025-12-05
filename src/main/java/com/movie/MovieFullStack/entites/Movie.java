package com.movie.MovieFullStack.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer movieId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "please provide movie title") // not null not empty
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "please provide director name")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "please provide studio name")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false)
    @NotBlank(message = "please provide releaseYear name")
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "please provide poster name")
    private String poster;
}
