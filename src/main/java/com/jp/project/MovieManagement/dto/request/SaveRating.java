package com.jp.project.MovieManagement.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record SaveRating(
        @Positive(message = "{generic.positive}") Long movieId,
        @Pattern(regexp = "[a-zA-Z0-9-_]{8,255}", message = "{saveUser.username.pattern}") String username,
        @Min(value = 0, message = "{generic.min}") @Max(value = 5, message = "{generic.max}") Integer rating
) {
}
