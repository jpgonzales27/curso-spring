package com.jp.project.MovieManagement.service.Impl;

import com.jp.project.MovieManagement.exception.ObjectNotFoundException;
import com.jp.project.MovieManagement.mapper.RatingMapper;
import com.jp.project.MovieManagement.persistence.entity.Rating;
import com.jp.project.MovieManagement.persistence.repository.RatingCrudRepository;
import com.jp.project.MovieManagement.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingCrudRepository ratingRepository;

    @Override
    public Page<Rating> findAll(Pageable pageable) {
        return ratingRepository.findAll(pageable);
    }

    @Override
    public Page<Rating> findAllByMovieId(Long movieId, Pageable pageable) {
        return ratingRepository.findByMovieId(movieId,pageable);
    }

    @Override
    public Page<Rating> findAllByUsername(String username, Pageable pageable) {
        return ratingRepository.findByUsername(username,pageable);
    }

    @Override
    public Rating findOneById(Long id) {
        return ratingRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("[rating:" + Long.toString(id) + "]"));
    }

    @Override
    public Rating createOne(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateOneById(Long id, Rating newRating) {

        Rating oldRating = this.findOneById(id);
        oldRating.setMovieId(newRating.getMovieId());
        oldRating.setUserId(newRating.getUserId());
        return ratingRepository.save(oldRating);
    }

    @Override
    public void deleteOneById(Long id) {

        /**
         * Esta es otra forma de eliminar tanto la que implementamos en MovieService como esta son validas
         */

        if(ratingRepository.existsById(id)){
            ratingRepository.deleteById(id);
            return;
        }

        throw new ObjectNotFoundException("[rating:" + Long.toString(id) + "]");
    }
}
