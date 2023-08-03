package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.enums.BackgroundColorType;
import com.gmail.javacoded78.enums.ColorSchemeType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projection.UserCommonProjection;

import com.gmail.javacoded78.security.JwtProvider;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public String updateUsername(String username) {
        if (username.length() == 0 || username.length() > 50) {
            throw new ApiRequestException("Incorrect username length", HttpStatus.BAD_REQUEST);
        }
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userRepository.updateUsername(username, authUserId);
        return username;
    }

    @Override
    @Transactional
    public Map<String, Object> updateEmail(String email) {
        Optional<UserCommonProjection> userByEmail = userRepository.getUserByEmail(email, UserCommonProjection.class);

        if (userByEmail.isEmpty()) {
            User user = authenticationService.getAuthenticatedUser();
            userRepository.updateEmail(email, user.getId());
            String token = jwtProvider.createToken(email, "USER");
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);
            return response;
        }
        throw new ApiRequestException("Email has already been taken.", HttpStatus.FORBIDDEN);
    }

    @Override
    @Transactional
    public Map<String, Object> updatePhone(String countryCode, Long phone) {
        int phoneLength = String.valueOf(phone).length();

        if (phoneLength < 6 || phoneLength > 10) {
            throw new ApiRequestException("Not valid phone number", HttpStatus.BAD_REQUEST);
        }
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userRepository.updatePhone(countryCode, phone, authUserId);
        return Map.of("countryCode", countryCode, "phone", phone);
    }

    @Override
    @Transactional
    public String updateCountry(String country) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userRepository.updateCountry(country, authUserId);
        return country;
    }

    @Override
    @Transactional
    public String updateGender(String gender) {
        if (gender.length() == 0 || gender.length() > 30) {
            throw new ApiRequestException("Incorrect gender length", HttpStatus.BAD_REQUEST);
        }
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userRepository.updateGender(gender, authUserId);
        return gender;
    }

    @Override
    @Transactional
    public String updateLanguage(String language) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userRepository.updateLanguage(language, authUserId);
        return language;
    }

    @Override
    @Transactional
    public boolean updateDirectMessageRequests(boolean mutedDirectMessages) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userRepository.updateDirectMessageRequests(mutedDirectMessages, authUserId);
        return mutedDirectMessages;
    }

    @Override
    @Transactional
    public boolean updatePrivateProfile(boolean privateProfile) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userRepository.updatePrivateProfile(privateProfile, authUserId);
        return privateProfile;
    }

    @Override
    @Transactional
    public ColorSchemeType updateColorScheme(ColorSchemeType colorSchemeType) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userRepository.updateColorScheme(colorSchemeType, authUserId);
        return colorSchemeType;
    }

    @Override
    @Transactional
    public BackgroundColorType updateBackgroundColor(BackgroundColorType backgroundColorType) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userRepository.updateBackgroundColor(backgroundColorType, authUserId);
        return backgroundColorType;
    }
}
