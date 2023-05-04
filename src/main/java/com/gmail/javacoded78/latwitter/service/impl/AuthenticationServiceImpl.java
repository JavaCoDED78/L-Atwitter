package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.dto.response.AuthenticationResponse;
import com.gmail.javacoded78.latwitter.mapper.UserMapper;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.security.JwtProvider;
import com.gmail.javacoded78.latwitter.service.AuthenticationService;
import com.gmail.javacoded78.latwitter.service.email.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final MailSender mailSender;
    private final UserMapper userMapper;

    @Value("${hostname}")
    private String hostname;

    @Override
    public AuthenticationResponse login(String email) {
        User user = userRepository.findByEmail(email);
        String token = jwtProvider.createToken(email, "USER");
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUser(userMapper.convertToUserResponse(user));
        response.setToken(token);
        return response;
    }

    @Override
    public boolean registration(User user) {
        User userFromDb = userRepository.findByEmail(user.getEmail());
        if (userFromDb != null) return false;
        user.setActive(false);
        user.setRole("USER");
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String subject = "Activation code";
        String template = "registration-template";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("fullName", user.getFullName());
        attributes.put("registrationUrl", "http://" + hostname + "/activate/" + user.getActivationCode());
//        mailSender.sendMessageHtml(user.getEmail(), subject, template, attributes);
        return true;
    }

    @Override
    public AuthenticationResponse getUserByToken() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setUser(userMapper.convertToUserResponse(user));
        return authenticationResponse;
    }


    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) return false;
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public User findByPasswordResetCode(String code) {
        return userRepository.findByPasswordResetCode(code);
    }

    @Override
    public boolean sendPasswordResetCode(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;
        user.setPasswordResetCode(UUID.randomUUID().toString());
        userRepository.save(user);

        String subject = "Password reset";
        String template = "password-reset-template";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("fullName", user.getFullName());
        attributes.put("resetUrl", "http://" + hostname + "/reset/" + user.getPasswordResetCode());
        mailSender.sendMessageHtml(user.getEmail(), subject, template, attributes);
        return true;
    }

    @Override
    public String passwordReset(String email, String password) {
        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPasswordResetCode(null);
        userRepository.save(user);
        return "Password successfully changed!";
    }
}
