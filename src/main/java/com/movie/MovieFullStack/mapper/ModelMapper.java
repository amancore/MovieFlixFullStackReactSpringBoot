package com.movie.MovieFullStack.mapper;

import com.movie.MovieFullStack.Dto.MovieDto;
import com.movie.MovieFullStack.entites.Movie;

public class ModelMapper {

    public static MovieDto convertMovieDto(Movie movie, String baseUrl){
        if(baseUrl==null || baseUrl.isEmpty()){
            baseUrl="/file/";
        }
        String posterUrl = baseUrl + movie.getPoster();   // build the URL

        return new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
    }

    public static Movie convertMovie(MovieDto movieDto){
        return new Movie(
                movieDto.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
    }
}
