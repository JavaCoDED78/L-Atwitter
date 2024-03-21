package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.enums.BackgroundColorType;
import com.gmail.javacoded78.enums.ColorSchemeType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.producer.UserProducer;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.UserSettingsRepository;
import com.gmail.javacoded78.repository.projection.AuthUserProjection;

import com.gmail.javacoded78.security.JwtProvider;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_HAS_ALREADY_BEEN_TAKEN;
import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_USERNAME_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.INVALID_GENDER_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.INVALID_PHONE_NUMBER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSettingsServiceImpl implements UserSettingsService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final JwtProvider jwtProvider;
    private final UserProducer userProducer;

    @Override
    @Transactional
    public String updateUsername(String username) {
        if (username.isEmpty() || username.length() > 50) {
            throw new ApiRequestException(INCORRECT_USERNAME_LENGTH, HttpStatus.BAD_REQUEST);
        }
        User user = authenticationService.getAuthenticatedUser();
        user.setUsername(username);
        userProducer.sendUserEvent(user);
        return username;
    }

    @Override
    @Transactional
    public Map<String, Object> updateEmail(String email) {
        if (!userSettingsRepository.isEmailExist(email)) {
            Long authUserId = authenticationService.getAuthenticatedUserId();
            userSettingsRepository.updateEmail(email, authUserId);
            String token = jwtProvider.createToken(email, "USER");
            AuthUserProjection user = userRepository.getUserById(authUserId, AuthUserProjection.class).orElse(null);
            return Map.of("user", user, "token", token);
        }
        throw new ApiRequestException(EMAIL_HAS_ALREADY_BEEN_TAKEN, HttpStatus.FORBIDDEN);
    }

    @Override
    @Transactional
    public Map<String, Object> updatePhone(String countryCode, Long phone) {
        int phoneLength = String.valueOf(phone).length();

        if (phoneLength < 6 || phoneLength > 10) {
            throw new ApiRequestException(INVALID_PHONE_NUMBER, HttpStatus.BAD_REQUEST);
        }
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updatePhone(countryCode, phone, authUserId);
        return Map.of("countryCode", countryCode, "phone", phone);
    }

    @Override
    @Transactional
    public String updateCountry(String country) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateCountry(country, authUserId);
        return country;
    }

    @Override
    @Transactional
    public String updateGender(String gender) {
        if (gender.isEmpty() || gender.length() > 30) {
            throw new ApiRequestException(INVALID_GENDER_LENGTH, HttpStatus.BAD_REQUEST);
        }
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateGender(gender, authUserId);
        return gender;
    }

    @Override
    @Transactional
    public String updateLanguage(String language) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateLanguage(language, authUserId);
        return language;
    }

    @Override
    @Transactional
    public boolean updateDirectMessageRequests(boolean mutedDirectMessages) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateDirectMessageRequests(mutedDirectMessages, authUserId);
        return mutedDirectMessages;
    }

    @Override
    @Transactional
    public boolean updatePrivateProfile(boolean privateProfile) {
        User user = authenticationService.getAuthenticatedUser();
        user.setPrivateProfile(privateProfile);
        userProducer.sendUserEvent(user);
        return privateProfile;
    }

    @Override
    @Transactional
    public ColorSchemeType updateColorScheme(ColorSchemeType colorSchemeType) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateColorScheme(colorSchemeType, authUserId);
        return colorSchemeType;
    }

    @Override
    @Transactional
    public BackgroundColorType updateBackgroundColor(BackgroundColorType backgroundColorType) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateBackgroundColor(backgroundColorType, authUserId);
        return backgroundColorType;
    }
}
