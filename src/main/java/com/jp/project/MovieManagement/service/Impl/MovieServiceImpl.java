package com.jp.project.MovieManagement.service.Impl;

import com.jp.project.MovieManagement.dto.request.MovieSearchCriteria;
import com.jp.project.MovieManagement.dto.request.SaveMovie;
import com.jp.project.MovieManagement.dto.response.GetMovie;
import com.jp.project.MovieManagement.exception.ObjectNotFoundException;
import com.jp.project.MovieManagement.mapper.MovieMapper;
import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.persistence.repository.MovieCrudRepository;
import com.jp.project.MovieManagement.persistence.specification.FindAllMoviesSpecification;
import com.jp.project.MovieManagement.service.MovieService;
import com.jp.project.MovieManagement.util.MovieGenre;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Override
    public Page<GetMovie> findAll(MovieSearchCriteria searchCriteria, Pageable pageable) {
        FindAllMoviesSpecification moviesSpecification = new FindAllMoviesSpecification(searchCriteria);
        Page<Movie> entities = movieCrudRepository.findAll(moviesSpecification,pageable);
        return entities.map(MovieMapper::toGetDto);
    }

    @Override
    public GetMovie findOneById(Long id) {
        return MovieMapper.toGetDto(this.findOneEntityById(id));
    }

    private Movie findOneEntityById(Long id) {
        return movieCrudRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("[movie:" + Long.toString(id) + "]"));
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

    @Override
    public Page<GetMovie> findAllByGenre(MovieGenre genre) {
        Sort.Direction direction = Sort.Direction.fromString("DESC");
        Sort sort = Sort.by(direction, "releaseYear").and(Sort.by(Sort.Direction.ASC, "id"));
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Movie> entities = movieCrudRepository.findAllByGenre(MovieGenre.DRAMA, pageable);
        return entities.map(MovieMapper::toGetDto);

    }
}
