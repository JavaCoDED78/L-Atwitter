package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.dto.response.AuthenticationResponse;
import com.gmail.javacoded78.latwitter.model.User;

public interface AuthenticationService {

     AuthenticationResponse login(String email);

    boolean registration(User user);

    AuthenticationResponse getUserByToken();

    boolean activateUser(String code);

    User findByPasswordResetCode(String code);

    boolean sendPasswordResetCode(String email);

    String passwordReset(String email, String password);
}
