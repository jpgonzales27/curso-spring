package com.jp.project.MovieManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPasswordException extends RuntimeException {

    private final String password;
    private final String passwordRepeated;
    private final String errorMessage;

    public InvalidPasswordException(String password,  String errorMessage) {
        this.password = password;
        this.passwordRepeated = password;
        this.errorMessage = errorMessage;
    }

    public InvalidPasswordException(String password, String passwordRepeated, String errorMessage) {
        this.password = password;
        this.passwordRepeated = passwordRepeated;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return "Invalid password: "+errorMessage;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
