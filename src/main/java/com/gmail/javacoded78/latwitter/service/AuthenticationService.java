package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.User;

import java.util.Map;

public interface AuthenticationService {

    User getAuthenticatedUser();

    Map<String, Object> login(String email, String password);

    String registration(String email, String username, String birthday);

    String sendRegistrationCode(String email);

    String activateUser(String code);

    Map<String, Object> endRegistration(String email, String password);

    Map<String, Object> getUserByToken();

    String findEmail(String email);

    User findByPasswordResetCode(String code);

    String sendPasswordResetCode(String email);

    String passwordReset(String email, String password, String password2);
}
