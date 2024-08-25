package com.jp.project.MovieManagement.service.Impl;

import com.jp.project.MovieManagement.dto.request.SaveMovie;
import com.jp.project.MovieManagement.dto.response.GetMovie;
import com.jp.project.MovieManagement.exception.ObjectNotFoundException;
import com.jp.project.MovieManagement.mapper.MovieMapper;
import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.persistence.repository.MovieCrudRepository;
import com.jp.project.MovieManagement.service.MovieService;
import com.jp.project.MovieManagement.util.MovieGenre;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Override
    public List<GetMovie> findAll() {
        List<Movie> entities = movieCrudRepository.findAll();
        return MovieMapper.toGetDtoList(entities);
    }

    @Override
    public List<GetMovie> findAllByTitle(String title) {
        List<Movie> entities = movieCrudRepository.findByTitleContaining(title);
        return MovieMapper.toGetDtoList(entities);
    }

    @Override
    public List<GetMovie> findAllByGenre(MovieGenre genre) {
        List<Movie> entities = movieCrudRepository.findByGenre(genre);
        return MovieMapper.toGetDtoList(entities);
    }

    @Override
    public List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title) {
        List<Movie> entities = movieCrudRepository.findByGenreAndTitleContains(genre, title);
        return MovieMapper.toGetDtoList(entities);
    }

    @Override
    public GetMovie findOneById(Long id) {
        return MovieMapper.toGetDto(this.findOneEntityById(id));
    }


    private Movie findOneEntityById(Long id) {
        return movieCrudRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Movie not found"));
//                .orElseThrow(() -> new ObjectNotFoundException("[movie:" + Long.toString(id) + "]"));
    }

    @Override
    public GetMovie saveOne(SaveMovie movieDto) {
        Movie movie = MovieMapper.toEntity(movieDto);
        return MovieMapper.toGetDto(movieCrudRepository.save(movie));
    }

    @Override
    public GetMovie updateOneById(Long id, SaveMovie newMovieDto) {
        Movie oldMovie = this.findOneEntityById(id);
        MovieMapper.updateEntity(oldMovie, newMovieDto);
        return MovieMapper.toGetDto(movieCrudRepository.save(oldMovie));
    }

    @Override
    public void deleteOneById(Long id) {
        Movie movie = this.findOneEntityById(id);
        movieCrudRepository.delete(movie);
    }
}
