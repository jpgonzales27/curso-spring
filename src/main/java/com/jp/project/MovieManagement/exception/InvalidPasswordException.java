package com.jp.project.MovieManagement.exception;

public class InvalidPasswordException extends RuntimeException {

    private final String password;
    private final String passwordRepeated;
    private final String errorMessage;

    public InvalidPasswordException(String password, String errorMessage) {
        this.password = password;
        this.passwordRepeated = password;
        this.errorMessage = errorMessage;
    }

    public InvalidPasswordException(String message, String password, String passwordRepeated, String errorMessage) {
        super(message);
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
