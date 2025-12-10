package com.movie.MovieFullStack.Dto;

import java.util.List;

public record MoviePageResponse (List<MovieDto> movieDto,
                                 Integer pageNumber,
                                 Integer pageSize,
                                 Integer totalPages,
                                 long totalElements, boolean isLast ){
}
