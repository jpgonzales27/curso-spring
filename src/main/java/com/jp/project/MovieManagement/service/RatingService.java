package com.jp.project.MovieManagement.service;

import com.jp.project.MovieManagement.dto.request.SaveRating;
import com.jp.project.MovieManagement.dto.response.GetCompleteRating;
import com.jp.project.MovieManagement.persistence.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingService {
    Page<GetCompleteRating> findAll(Pageable pageable);
    Page<GetCompleteRating> findAllByMovieId(Long movieId,Pageable pageable);
    Page<GetCompleteRating> findAllByUsername(String username,Pageable pageable);
    GetCompleteRating findOneById(Long id);
    GetCompleteRating createOne(SaveRating ratingDto);
    GetCompleteRating updateOneById(Long id, SaveRating ratingDto);
    void deleteOneById(Long id);
}
