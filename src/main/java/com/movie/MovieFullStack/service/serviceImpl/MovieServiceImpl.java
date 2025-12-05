package com.movie.MovieFullStack.service.serviceImpl;

import com.movie.MovieFullStack.Dto.MovieDto;
import com.movie.MovieFullStack.entites.Movie;
import com.movie.MovieFullStack.mapper.ModelMapper;
import com.movie.MovieFullStack.repository.MovieRepository;
import com.movie.MovieFullStack.service.FileService;
import com.movie.MovieFullStack.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Value("${project.poster}")
    private String path;
    private final FileService fileService;
    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        // 1. upload the file
        String uploadedFileName = fileService.uploadFile(path,file);

        // 2. set the value of the field 'poster' as the file name
        movieDto.setPoster(uploadedFileName);

        // 3. map dto to movie object
        Movie movie = ModelMapper.convertMovie(movieDto);

        // 4. save the movie object -> saved movie object
        Movie savedMovie = movieRepository.save(movie);

        // 5. generate the posterUrl
        // 6. map movie object to Dto object and return it
        return ModelMapper.convertMovieDto(savedMovie,"/file/");
    }
/*posterUrl is NOT stored in DB → Good.
posterUrl must be generated at runtime → Correct.
Mapper must not include posterUrl → Movie → Correct.
Mapper should include posterUrl → MovieDto → Required.*/

    @Override
    public MovieDto getMovie(Integer movieId) {
        // 1. check the data in the db if exists , fetch the data of the given id
        Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException("movie not find on this id "+ movieId));
        // 2. generate posterUrl
        // 3. map to movieDto object and return it
        return ModelMapper.convertMovieDto(movie, "/file/");
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(m->ModelMapper.convertMovieDto(m,"/file/"))
                .toList();
    }
}
