package com.jp.project.MovieManagement.dto.response;

import com.jp.project.MovieManagement.util.MovieGenre;

import java.io.Serializable;
import java.util.List;

public record GetMovie(
        Long id,
        String title,
        String director,
        MovieGenre genre,
        int releaseYear,
        int totalRatings
) implements Serializable {

    public static record GetRating(
            Long id,
            int ratings,
            String username
    ) implements Serializable {}
}
