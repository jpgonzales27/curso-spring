package com.jp.project.MovieManagement.service.validator;

import com.jp.project.MovieManagement.exception.InvalidPasswordException;
import org.springframework.util.StringUtils;

public class PasswordValidator {

    public static void validatedPassword (String password , String passwordRepeated) {

        if(!StringUtils.hasText(password) || !StringUtils.hasText(passwordRepeated)){
            throw new IllegalArgumentException("Passwords must contain data");
        }

        if(!password.equals(passwordRepeated)){
            throw new InvalidPasswordException(password, passwordRepeated, "Passwords do not match");
        }

        if(!containsLowercase(password)){
            throw new InvalidPasswordException(password, "Passwords must contain at least one lowercase letter");
        }

        if(!containsUppercase(password)){
            throw new InvalidPasswordException(password, "Passwords must contain at least one uppercase letter");
        }

        if(!containsNumber(password)){
            throw new InvalidPasswordException(password, "Passwords must contain at least one number");
        }

        if(!containsSpecialCharacter(password)){
            throw new InvalidPasswordException(password, "Passwords must contain at least one especial character");
        }
    }


    private static boolean containsSpecialCharacter(String password){
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*");
    }

    private static boolean containsLowercase(String password){
        return password.matches(".*[a-z].*");
    }

    private static boolean containsUppercase(String password){
        return password.matches(".*[A-Z].*");
    }

    private static boolean containsNumber(String password){
        return password.matches(".*\\d.*");
    }
}
