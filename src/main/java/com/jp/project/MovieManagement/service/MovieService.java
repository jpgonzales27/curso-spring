package com.jp.project.MovieManagement.service;

import com.jp.project.MovieManagement.dto.request.MovieSearchCriteria;
import com.jp.project.MovieManagement.dto.request.SaveMovie;
import com.jp.project.MovieManagement.dto.response.GetMovie;
import com.jp.project.MovieManagement.dto.response.GetMovieDetails;
import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.util.MovieGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    Page<GetMovie> findAll(MovieSearchCriteria searchCriteria,Pageable pageable);
    GetMovieDetails findOneById(Long id);
    GetMovie saveOne (SaveMovie movieDto);
    GetMovie updateOneById(Long id, SaveMovie movieDto);
    void deleteOneById(Long id);
    Page<GetMovie> findAllByGenre(MovieGenre genre);
}
