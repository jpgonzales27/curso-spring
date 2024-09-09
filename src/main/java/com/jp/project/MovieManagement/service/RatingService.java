package com.jp.project.MovieManagement.service;

import com.jp.project.MovieManagement.persistence.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingService {
    Page<Rating> findAll(Pageable pageable);
    Page<Rating> findAllByMovieId(Long movieId,Pageable pageable);
    Page<Rating> findAllByUsername(String username,Pageable pageable);
    Rating findOneById(Long id);
    Rating createOne(Rating rating);
    Rating updateOneById(Long id, Rating rating);
    void deleteOneById(Long id);
}
