package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.exception.ApiRequestException;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.security.JwtProvider;
import com.gmail.javacoded78.latwitter.service.AuthenticationService;
import com.gmail.javacoded78.latwitter.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User updateUsername(String username) {
        User user = authenticationService.getAuthenticatedUser();
        user.setUsername(username);
        return userRepository.save(user);
    }

    @Override
    public Map<String, Object> updateEmail(String email) {
        User user = authenticationService.getAuthenticatedUser();
        User userByEmail = userRepository.findByEmail(email);

        if (userByEmail == null) {
            user.setEmail(email);
            userRepository.save(user);
            String token = jwtProvider.createToken(email, "USER");
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);
            return response;
        }
        throw new ApiRequestException("Email has already been taken.", HttpStatus.FORBIDDEN);
    }

    @Override
    public User updatePhone(String countryCode, Long phone) {
        User user = authenticationService.getAuthenticatedUser();
        user.setCountryCode(countryCode);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    @Override
    public User updateCountry(String country) {
        User user = authenticationService.getAuthenticatedUser();
        user.setCountry(country);
        return userRepository.save(user);
    }

    @Override
    public User updateGender(String gender) {
        User user = authenticationService.getAuthenticatedUser();
        user.setGender(gender);
        return userRepository.save(user);
    }

    @Override
    public User updateLanguage(String language) {
        User user = authenticationService.getAuthenticatedUser();
        user.setLanguage(language);
        return userRepository.save(user);
    }

    @Override
    public User updateDirectMessageRequests(boolean mutedDirectMessages) {
        User user = authenticationService.getAuthenticatedUser();
        user.setMutedDirectMessages(mutedDirectMessages);
        return userRepository.save(user);
    }

    @Override
    public User updatePrivateProfile(boolean privateProfile) {
        User user = authenticationService.getAuthenticatedUser();
        user.setPrivateProfile(privateProfile);
        return userRepository.save(user);
    }
}
