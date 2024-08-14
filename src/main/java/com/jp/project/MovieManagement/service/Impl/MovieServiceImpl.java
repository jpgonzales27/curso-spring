package com.jp.project.MovieManagement.service.Impl;

import com.jp.project.MovieManagement.exception.ObjectNotFoundException;
import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.persistence.repository.MovieCrudRepository;
import com.jp.project.MovieManagement.service.MovieService;
import com.jp.project.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Override
    public List<Movie> findAll() {
        return movieCrudRepository.findAll();
    }

    @Override
    public List<Movie> findAllByTitle(String title) {
        return movieCrudRepository.findByTitleContaining(title);
    }

    @Override
    public List<Movie> findAllByGenre(MovieGenre genre) {
        return movieCrudRepository.findByGenre(genre);
    }

    @Override
    public List<Movie> findAllByGenreAndTitle(MovieGenre genre, String title) {
        return movieCrudRepository.findByGenreAndTitleContains(genre, title);
    }

    @Override
    public Movie findOneById(Long id) {
        return movieCrudRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("[movie:" + Long.toString(id) + "]"));
    }

    @Override
    public Movie saveOne(Movie movie) {
        return movieCrudRepository.save(movie);
    }

    @Override
    public Movie updateOneById(Long id, Movie newMovie) {
        Movie oldMovie = this.findOneById(id);
        oldMovie.setTitle(newMovie.getTitle());
        oldMovie.setGenre(newMovie.getGenre());
        oldMovie.setReleaseYear(newMovie.getReleaseYear());
        oldMovie.setDirector(newMovie.getDirector());

        return movieCrudRepository.save(oldMovie);
    }

    @Override
    public void deleteOneById(Long id) {
        Movie movie = this.findOneById(id);
        movieCrudRepository.delete(movie);
    }
}
