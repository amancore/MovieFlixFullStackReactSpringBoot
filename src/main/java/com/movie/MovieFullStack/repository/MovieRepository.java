package com.movie.MovieFullStack.repository;

import com.movie.MovieFullStack.entites.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

}
