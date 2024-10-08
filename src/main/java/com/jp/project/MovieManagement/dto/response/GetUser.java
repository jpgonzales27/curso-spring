package com.jp.project.MovieManagement.dto.response;

import java.io.Serializable;
import java.util.List;

public record GetUser(
        String username,
        String name,
        int totalRatings
) implements Serializable {

    public static record GetRating(
            Long id,
            String movieTitle,
            Long movieId,
            int rating
    ) implements Serializable {}
}
