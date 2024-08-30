package com.jp.project.MovieManagement.persistence.repository;

import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.util.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MovieCrudRepository extends JpaRepository<Movie,Long>, JpaSpecificationExecutor<Movie> {

//    List<Movie> findByTitleContaining(String title);
//    List<Movie> findByGenre(MovieGenre genre);
//    List<Movie> findByGenreAndTitleContains(MovieGenre genre, String title);
//    List<Movie> findByGenreAndTitleContainingAndReleaseYearGreaterThanEqual(MovieGenre genre, String title, Integer minReleaseYear);
}
