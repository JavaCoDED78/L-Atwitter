package com.gmail.javacoded78.service;


import com.gmail.javacoded78.dto.request.AuthenticationRequest;
import com.gmail.javacoded78.dto.request.RegistrationRequest;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.projection.AuthUserProjection;
import com.gmail.javacoded78.repository.projection.UserPrincipalProjection;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface AuthenticationService {

    Long getAuthenticatedUserId();

    User getAuthenticatedUser();

    UserPrincipalProjection getUserPrincipalByEmail(String email);

    Map<String, Object> login(AuthenticationRequest request, BindingResult bindingResult);

    String registration(RegistrationRequest request, BindingResult bindingResult);

    String sendRegistrationCode(String email);

    String activateUser(String code);

    Map<String, Object> endRegistration(String email, String password);

    Map<String, Object> getUserByToken();

    String getEmail(String email);

    AuthUserProjection getByPasswordResetCode(String code);

    String sendPasswordResetCode(String email);

    String passwordReset(String email, String password, String password2);

    String currentPasswordReset(String currentPassword, String password, String password2);
}
