package com.jp.project.MovieManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record SaveUser(
    @Pattern(regexp="[a-zA-Z0-9-_]{6,255}" , message = "{saveUser.username.pattern}")
    String username,

    @Size(max = 255, message = "{generic.size}")
    String name,

    @Size(min = 10 , max = 255, message = "{generic.size}")
    @NotBlank(message = "{generic.notblank}")
    String password,

    @Size(min = 10 , max = 255, message = "{generic.size}")
    @NotBlank(message = "{generic.notblank}")
    String passwordRepeated
) implements Serializable {

}
