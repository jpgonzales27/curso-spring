package com.jp.project.MovieManagement.persistence.repository;

import com.jp.project.MovieManagement.persistence.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingCrudRepository extends JpaRepository<Rating,Long> {

    List<Rating> findByMovieId(long id);
}
