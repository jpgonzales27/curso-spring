package com.jp.project.MovieManagement.service;

import com.jp.project.MovieManagement.dto.request.SaveMovie;
import com.jp.project.MovieManagement.dto.response.GetMovie;
import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.util.MovieGenre;

import java.util.List;

public interface MovieService {

    List<GetMovie> findAll();
    List<GetMovie> findAllByTitle(String title);
    List<GetMovie> findAllByGenre(MovieGenre genre);
    List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title);

    GetMovie findOneById(Long id);
    GetMovie saveOne (SaveMovie movieDto);
    GetMovie updateOneById(Long id, SaveMovie movieDto);
    void deleteOneById(Long id);
}
