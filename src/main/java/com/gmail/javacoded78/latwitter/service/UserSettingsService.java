package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.BackgroundColorType;
import com.gmail.javacoded78.latwitter.model.ColorSchemeType;
import com.gmail.javacoded78.latwitter.model.User;

import java.util.Map;

public interface UserSettingsService {

    User updateUsername(String username);

    Map<String, Object> updateEmail(String email);

    User updatePhone(String countryCode, Long phone);

    User updateCountry(String country);

    User updateGender(String gender);

    User updateLanguage(String language);

    User updateDirectMessageRequests(boolean mutedDirectMessages);

    User updatePrivateProfile(boolean privateProfile);

    User updateColorScheme(ColorSchemeType colorSchemeType);

    User updateBackgroundColor(BackgroundColorType backgroundColorType);
}
