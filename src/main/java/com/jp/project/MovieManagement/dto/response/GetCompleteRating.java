package com.jp.project.MovieManagement.dto.response;

import java.io.Serializable;

public record GetCompleteRating(
        long ratingId,
        long movieId,
        String movieTitle,
        String username,
        int rating
) implements Serializable {
}
