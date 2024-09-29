package com.jp.project.MovieManagement.dto.response;

import com.jp.project.MovieManagement.util.MovieGenre;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetUserDetails(
    String username,
    LocalDateTime createAt,
    int totalRatings,
    double averageRating,
    int lowestRating,
    int highestRating
) implements Serializable {
}
