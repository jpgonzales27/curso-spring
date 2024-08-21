package com.jp.project.MovieManagement.mapper;

import com.jp.project.MovieManagement.dto.request.SaveMovie;
import com.jp.project.MovieManagement.dto.response.GetMovie;
import com.jp.project.MovieManagement.persistence.entity.Movie;

import java.util.List;

public class MovieMapper {

    public static GetMovie toGetDto(Movie entity) {
        if(entity == null) return null;

        return new GetMovie(
                entity.getId(),
                entity.getTitle(),
                entity.getDirector(),
                entity.getGenre(),
                entity.getReleaseYear(),
                RatingMapper.toGetMovieRatingDtoList(entity.getRatings())
        );
    }

    public static List<GetMovie> toGetDtoList(List<Movie> entities) {
        if(entities == null) return null;

        return entities.stream() //List<Movie> -> Stream<Movie>
                .map(MovieMapper::toGetDto) // Stream<Movie> -> Stream<GetMovie>
                .toList();
    }

    public static Movie toEntity (SaveMovie saveDto) {
        if(saveDto == null) return null;

        Movie newMovie = new Movie();
        newMovie.setTitle(saveDto.title());
        newMovie.setDirector(saveDto.director());
        newMovie.setReleaseYear(saveDto.releaseYear());
        newMovie.setGenre(saveDto.genre());

        return newMovie;
    }

    public static void updateEntity(Movie oldMovie, SaveMovie newMovieDto) {
        if(oldMovie == null || newMovieDto == null) return;

        oldMovie.setTitle(newMovieDto.title());
        oldMovie.setGenre(newMovieDto.genre());
        oldMovie.setReleaseYear(newMovieDto.releaseYear());
        oldMovie.setDirector(newMovieDto.director());
    }
}
