package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.User;

import java.util.Map;

public interface AuthenticationService {

    Map<String, Object> login(String email);

    boolean registration(User user);

    boolean activateUser(String code);

    User findByPasswordResetCode(String code);

    boolean sendPasswordResetCode(String email);

    String passwordReset(String email, String password);
}
