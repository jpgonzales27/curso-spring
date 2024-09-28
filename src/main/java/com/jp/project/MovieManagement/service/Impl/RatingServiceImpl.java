package com.jp.project.MovieManagement.service.Impl;

import com.jp.project.MovieManagement.dto.request.SaveRating;
import com.jp.project.MovieManagement.dto.response.GetCompleteRating;
import com.jp.project.MovieManagement.dto.response.GetMovie;
import com.jp.project.MovieManagement.dto.response.GetUser;
import com.jp.project.MovieManagement.exception.DuplicateRatingException;
import com.jp.project.MovieManagement.exception.ObjectNotFoundException;
import com.jp.project.MovieManagement.mapper.RatingMapper;
import com.jp.project.MovieManagement.persistence.entity.Rating;
import com.jp.project.MovieManagement.persistence.entity.User;
import com.jp.project.MovieManagement.persistence.repository.RatingCrudRepository;
import com.jp.project.MovieManagement.service.RatingService;
import com.jp.project.MovieManagement.service.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingCrudRepository ratingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<GetCompleteRating> findAll(Pageable pageable) {
        return ratingRepository.findAll(pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Override
    public Page<GetMovie.GetRating> findAllByMovieId(Long movieId, Pageable pageable) {
        return ratingRepository.findByMovieId(movieId, pageable).map(RatingMapper::toGetMovieRatingDto);
    }

    @Override
    public Page<GetUser.GetRating> findAllByUsername(String username, Pageable pageable) {
        return ratingRepository.findByUsername(username, pageable).map(RatingMapper::toGetUserRatingDto);
    }

    @Override
    public GetCompleteRating findOneById(Long id) {
        return RatingMapper.toGetCompleteRatingDto(this.findOneEntityById(id));
    }

    private Rating findOneEntityById(Long id) {
        return ratingRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("[rating:" + Long.toString(id) + "]"));
    }

    @Override
    public GetCompleteRating createOne(SaveRating ratingDto) {

        //option 1
        boolean ratingExits = ratingRepository.existsByMovieIdAndUserUsername(ratingDto.movieId(),ratingDto.username());
        if(ratingExits) {
            throw new DuplicateRatingException(ratingDto.username(),ratingDto.movieId());
        }

        //Option 2
//        Long ratingId = ratingRepository.getRatingIdByMovieIdAndUsername(saveDto.movieId(), saveDto.username());
//        if(ratingId != null && ratingId.longValue() > 0){
//            return this.updateOneById(ratingId, saveDto);
//        }

        User userEntity = userService.findOneEntityByUsername(ratingDto.username());
        Rating rating = RatingMapper.toEntity(ratingDto, userEntity.getId());
        ratingRepository.save(rating);
        entityManager.detach(rating);

        return ratingRepository.findById(rating.getId())
                .map(RatingMapper::toGetCompleteRatingDto)
                .get();
    }

    @Override
    public GetCompleteRating updateOneById(Long id, SaveRating ratingDto) {
        Rating oldRating = this.findOneEntityById(id);
        User userEntity = userService.findOneEntityByUsername(ratingDto.username());
        RatingMapper.updateEntity(oldRating, ratingDto,userEntity.getId());
        return RatingMapper.toGetCompleteRatingDto(ratingRepository.save(oldRating));
    }

    @Override
    public void deleteOneById(Long id) {

        /**
         * Esta es otra forma de eliminar tanto la que implementamos en MovieService como esta son validas
         */

        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
            return;
        }

        throw new ObjectNotFoundException("[rating:" + Long.toString(id) + "]");
    }
}
