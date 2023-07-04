package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.request.RegistrationRequest;
import com.gmail.javacoded78.latwitter.dto.response.AuthenticationResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticationMapper {

    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    private User convertToEntity(RegistrationRequest registrationRequest) {
        return modelMapper.map(registrationRequest, User.class);
    }

    public AuthenticationResponse login(String email) {
        Map<String, Object> credentials = authenticationService.login(email);
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUser(userMapper.convertToUserResponse((User) credentials.get("user")));
        response.setToken((String) credentials.get("token"));
        return response;
    }

    public boolean registration(RegistrationRequest registrationRequest) {
        return authenticationService.registration(convertToEntity(registrationRequest));
    }

    public AuthenticationResponse getUserByToken() {
        Map<String, Object> credentials = authenticationService.getUserByToken();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setUser(userMapper.convertToUserResponse((User) credentials.get("user")));
        authenticationResponse.setToken((String) credentials.get("token"));
        return authenticationResponse;
    }

    public boolean activateUser(String code) {
        return authenticationService.activateUser(code);
    }

    public boolean sendPasswordResetCode(String email) {
        return authenticationService.sendPasswordResetCode(email);
    }

    public UserResponse findByPasswordResetCode(String code) {
        return userMapper.convertToUserResponse(authenticationService.findByPasswordResetCode(code));
    }

    public String passwordReset(String email, String password) {
        return authenticationService.passwordReset(email, password);
    }
}
