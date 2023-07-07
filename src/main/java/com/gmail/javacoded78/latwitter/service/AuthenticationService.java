package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.User;

import java.util.Map;

public interface AuthenticationService {

    boolean findEmail(String email);

    Map<String, Object> login(String email);

    boolean registration(String email, String username, String birthday);

    void sendRegistrationCode(String email);

    Map<String, Object> endRegistration(String email, String password);

    Map<String, Object> getUserByToken();

    boolean activateUser(String code);

    User findByPasswordResetCode(String code);

    void sendPasswordResetCode(String email);

    String passwordReset(String email, String password);
}
