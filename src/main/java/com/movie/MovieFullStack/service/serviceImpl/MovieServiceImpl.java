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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Movie movie = ModelMapper.convertMovie(movieDto,null);

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

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        // 1. check if the movie exists or not with the given id
       Movie movie =  movieRepository.findById(movieId).orElseThrow(()->new NullPointerException("Movie was not found by this id : " + movieId) );

        // 2. if movie exists  , 2.1 either change the file    , 2.2 or just change the data
        // if file not null , delete the existing file associated with the record, and uplaod the new file
        String fileName = movie.getPoster();
        if(file!=null){
            String delFilePath = path + File.separator + fileName;
            Files.deleteIfExists(Paths.get(delFilePath));
            fileName = fileService.uploadFile(path, file);
        }
        // 3. set movie dto poster value according to the step 2
        movieDto.setPoster(fileName);

        // 4. map it to movie object
        Movie newMovie = ModelMapper.convertMovie(movieDto, movie.getMovieId());

        // 5. save the movie object return the movie obj
        Movie updatedMovie = movieRepository.save(newMovie);

        // 6. generaate the posterUrl for this

        // 7 . map the movieDto to the movie obj and return it
        return ModelMapper.convertMovieDto(updatedMovie,"/file/");
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        // 1. check if the movie exists in the db delete it
        System.out.println(movieId);
        Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException("movie was not found by this id :" + movieId ));
        Integer id = movie.getMovieId();
        // 2. delete the file associated with this obj
        String delFilePath = path + File.separator + movie.getPoster();
        Files.deleteIfExists(Paths.get(delFilePath));

        // 3. delete the movie obj
        movieRepository.delete(movie);
        return "movie was delete by this id : " + id ;
    }
}
