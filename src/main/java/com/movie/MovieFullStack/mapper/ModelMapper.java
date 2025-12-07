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

    public static Movie convertMovie(MovieDto movieDto, Integer movieId){
        if(movieId==null){
            movieId=movieDto.getMovieId();
        }
        return new Movie(
                //                movieDto.getMovieId(),
//                null,  // if the primary key value is null : insert it | else update it on the place of the id
                movieId,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
    }
}
