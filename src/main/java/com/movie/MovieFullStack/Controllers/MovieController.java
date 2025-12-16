package com.movie.MovieFullStack.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.MovieFullStack.Dto.MovieDto;
import com.movie.MovieFullStack.Dto.MoviePageResponse;
import com.movie.MovieFullStack.exceptions.EmptyFileException;
import com.movie.MovieFullStack.service.MovieService;
import com.movie.MovieFullStack.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(
            value = "/add-movie",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart("file") MultipartFile file, @RequestParam("movieDto") String movieDtoJson) throws IOException {

        if(file.isEmpty()){
            throw new EmptyFileException("File is empty please send another file");
        }
        // Convert JSON string into MovieDto object
        ObjectMapper objectMapper = new ObjectMapper();
        MovieDto movieDto = objectMapper.readValue(movieDtoJson, MovieDto.class);

        // Call service
        MovieDto savedMovie = movieService.addMovie(movieDto, file);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId){
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping(
            value = "/update/{movieId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<MovieDto> updateMovieById(@PathVariable Integer movieId,@RequestPart("file") MultipartFile file,@RequestParam("movieDto") @RequestPart String movieDtoJson) throws IOException {
        if(file.isEmpty()){
            file = null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        MovieDto movieDto = objectMapper.readValue(movieDtoJson, MovieDto.class);
        return ResponseEntity.ok(movieService.updateMovie(movieId,movieDto, file));
    }

    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteMovieById(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }

    @GetMapping("/allMoviesPage")
    public ResponseEntity<MoviePageResponse> getAllMovieWithPagination(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize){
        return ResponseEntity.ok(movieService.getAllMovieWithPagination(pageNumber, pageSize));
    }
    @GetMapping("allMoviesPageSort")
    public ResponseEntity<MoviePageResponse> getAllMovieWithPaginationAndSorting(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR,required = false) String dir
            ){
        return ResponseEntity.ok(movieService.getAllMovieWithPaginationAndSorting(pageNumber,pageSize,sortBy,dir));
    }

}
