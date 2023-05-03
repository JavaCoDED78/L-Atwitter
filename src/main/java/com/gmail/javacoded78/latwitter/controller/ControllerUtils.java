package com.gmail.javacoded78.latwitter.controller;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class ControllerUtils {

    static boolean isPasswordConfirmEmpty(String password2) {
        return StringUtils.hasText(password2);
    }

    static boolean isPasswordDifferent(String password, String password2) {
        return password != null && !password.equals(password2);
    }
}
