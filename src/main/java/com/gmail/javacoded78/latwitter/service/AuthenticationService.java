package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.User;

import java.util.Map;

public interface AuthenticationService {

    boolean findEmail(String email);

    Map<String, Object> login(String email);

    boolean registration(User user);

    Map<String, Object> getUserByToken();

    boolean activateUser(String code);

    User findByPasswordResetCode(String code);

    void sendPasswordResetCode(String email);

    String passwordReset(String email, String password);
}
