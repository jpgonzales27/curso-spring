package com.jp.project.MovieManagement.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jp.project.MovieManagement.util.MovieGenre;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

public record SaveMovie(
        @NotBlank(message ="{generic.notblank}" )
        @Size(min = 4, max = 255,message = "{generic.size}")
        String title,

        @NotBlank(message ="{generic.notblank}" )
        @Size(min = 4, max = 255,message = "{generic.size}")
        String director,

        MovieGenre genre,

        @Min(value = 1900, message = "{generic.min}")
        int releaseYear

//        @JsonProperty("availability_end_time")
//        @JsonFormat(pattern = "yyyy-MM-dd")
//        @FutureOrPresent
//        LocalDate availabilityEndTime
) implements Serializable {
}
