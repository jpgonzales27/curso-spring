package com.jp.project.MovieManagement.dto.request;

import java.io.Serializable;

public record SaveUser(
    String username,
    String name,
    String password,
    String passwordRepeated
) implements Serializable {

}
