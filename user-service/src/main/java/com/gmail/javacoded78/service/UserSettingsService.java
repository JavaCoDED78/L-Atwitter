package com.gmail.javacoded78.service;

import com.gmail.javacoded78.enums.BackgroundColorType;
import com.gmail.javacoded78.enums.ColorSchemeType;

import java.util.Map;

public interface UserSettingsService {

    String updateUsername(String username);

    Map<String, Object> updateEmail(String email);

    Map<String, Object> updatePhone(String countryCode, Long phone);

    String updateCountry(String country);

    String updateGender(String gender);

    String updateLanguage(String language);

    boolean updateDirectMessageRequests(boolean mutedDirectMessages);

    boolean updatePrivateProfile(boolean privateProfile);

    ColorSchemeType updateColorScheme(ColorSchemeType colorSchemeType);

    BackgroundColorType updateBackgroundColor(BackgroundColorType backgroundColorType);
}
