package com.jp.project.MovieManagement.service;

import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.util.MovieGenre;

import java.util.List;

public interface MovieService {

    List<Movie> findAll();
    List<Movie> findAllByTitle(String title);
    List<Movie> findAllByGenre(MovieGenre genre);
    List<Movie> findAllByGenreAndTitle(MovieGenre genre, String title);

    Movie findOneById(Long id);
    Movie saveOne (Movie movie);
    Movie updateOneById(Long id, Movie movie);
    void deleteOneById(Long id);
}
