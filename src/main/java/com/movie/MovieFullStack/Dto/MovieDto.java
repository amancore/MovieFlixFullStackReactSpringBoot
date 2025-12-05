package com.movie.MovieFullStack.Dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieDto {
    private Integer movieId;

    @NotBlank(message = "please provide movie title") // not null not empty
    private String title;

    @NotBlank(message = "please provide director name")
    private String director;

    @NotBlank(message = "please provide studio name")
    private String studio;

    private Set<String> movieCast;

    private Integer releaseYear;

    @NotBlank(message = "please provide poster name")
    private String poster;

    @NotBlank(message = "please provide posterUrl name")
    private String posterUrl;  // actual image data link

}
