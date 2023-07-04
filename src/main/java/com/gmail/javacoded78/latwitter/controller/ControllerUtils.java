package com.gmail.javacoded78.latwitter.controller;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

public class ControllerUtils {

    protected static boolean isPasswordConfirmEmpty(String password2) {
        return StringUtils.isEmpty(password2);
    }

    protected static boolean isPasswordDifferent(String password, String password2) {
        return password != null && !password.equals(password2);
    }
}
