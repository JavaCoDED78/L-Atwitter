package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.projection.user.AuthUserProjection;

import java.util.Map;

public interface AuthenticationService {

    Long getAuthenticatedUserId();

    User getAuthenticatedUser();

    Map<String, Object> login(String email, String password);

    String registration(String email, String username, String birthday);

    String sendRegistrationCode(String email);

    String activateUser(String code);

    Map<String, Object> endRegistration(String email, String password);

    Map<String, Object> getUserByToken();

    String findEmail(String email);

    AuthUserProjection findByPasswordResetCode(String code);

    String sendPasswordResetCode(String email);

    String passwordReset(String email, String password, String password2);

    String currentPasswordReset(String currentPassword, String password, String password2);
}
