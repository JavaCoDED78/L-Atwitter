package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.request.AuthenticationRequest;
import com.gmail.javacoded78.dto.request.RegistrationRequest;
import com.gmail.javacoded78.models.User;
import com.gmail.javacoded78.repository.projection.AuthUserProjection;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface AuthenticationService {

    Long getAuthenticatedUserId();

    User getAuthenticatedUser();

    Map<String, Object> login(AuthenticationRequest request, BindingResult bindingResult);

    String registration(RegistrationRequest request, BindingResult bindingResult);

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
