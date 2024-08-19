package com.jp.project.MovieManagement.dto.request;

import com.jp.project.MovieManagement.util.MovieGenre;

import java.io.Serializable;

public record SaveMovie(
        String title,
        String director,
        MovieGenre genre,
        int releaseYear
) implements Serializable {
}
