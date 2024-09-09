package com.jp.project.MovieManagement.mapper;

import com.jp.project.MovieManagement.dto.request.SaveRating;
import com.jp.project.MovieManagement.dto.response.GetCompleteRating;
import com.jp.project.MovieManagement.dto.response.GetMovie;
import com.jp.project.MovieManagement.dto.response.GetUser;
import com.jp.project.MovieManagement.persistence.entity.Rating;

import java.util.List;

public class RatingMapper {

    public static GetMovie.GetRating toGetMovieRatingDto(Rating entity) {
        if (entity == null) return null;

        String username = entity.getUser() != null
                ? entity.getUser().getUsername()
                : null;

        return new GetMovie.GetRating(
                entity.getId(),
                entity.getRating(),
                username
        );
    }

    public static List<GetMovie.GetRating> toGetMovieRatingDtoList(List<Rating> entities) {
        if (entities == null) return null;

        //return entities.stream().map(entity-> RatingMapper.toGetMovieRatingDto(entity)).toList();
        return entities.stream().map(RatingMapper::toGetMovieRatingDto).toList();
    }

    public static GetUser.GetRating toGetUserRatingDto(Rating entity) {
        if (entity == null) return null;

        String movieTitle = entity.getMovie() != null
                ? entity.getMovie().getTitle()
                : null;

        return new GetUser.GetRating(
                entity.getId(),
                movieTitle,
                entity.getMovieId(),
                entity.getRating()
        );
    }

    public static List<GetUser.GetRating> toGetUserRatingDtoList(List<Rating> entities) {
        if(entities == null) return null;

        return entities.stream()
                .map(RatingMapper::toGetUserRatingDto)
                .toList();
    }

    public static GetCompleteRating toGetCompleteRatingDto(Rating entity){
        if ( entity == null ) return null;

        String movieTitle = entity.getMovie() != null ? entity.getMovie().getTitle() : null;
        String username = entity.getUser() != null ? entity.getUser().getUsername() : null;

        return new GetCompleteRating(
                entity.getId(),
                entity.getMovieId(),
                movieTitle,
                username,
                entity.getRating()
        );
    }

    public static Rating toEntity(SaveRating entityDto,Long userId) {
        if ( entityDto == null ) return null;

        Rating entity = new Rating();
        entity.setMovieId(entityDto.movieId());
        entity.setUserId(userId);
        entity.setRating(entityDto.rating());

        return entity;
    }

    public static void updateEntity(Rating entity, SaveRating dto, Long userId){
        if ( entity == null || dto== null ) return;

        entity.setUserId(userId);
        entity.setMovieId(dto.movieId());
        entity.setRating(dto.rating());
    }
}
