package com.jp.project.MovieManagement.dto.response;

import com.jp.project.MovieManagement.util.MovieGenre;

import java.io.Serializable;

public record GetMovieDetails(
        Long id,
        String title,
        String director,
        MovieGenre genre,
        int totalRatings,
        int releaseYear,
        double averageRatings,
        int lowestRating,
        int highestRating
) implements Serializable {
}
