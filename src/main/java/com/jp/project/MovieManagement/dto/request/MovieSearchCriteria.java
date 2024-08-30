package com.jp.project.MovieManagement.dto.request;

import com.jp.project.MovieManagement.util.MovieGenre;

public record MovieSearchCriteria(
    String title,
    MovieGenre genre,
    Integer minReleaseYear
) {
}
