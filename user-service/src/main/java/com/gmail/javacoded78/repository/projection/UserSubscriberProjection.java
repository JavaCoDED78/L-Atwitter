package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.common.enums.BackgroundColorType;
import com.gmail.javacoded78.common.enums.ColorSchemeType;

import java.time.LocalDateTime;

public interface UserSubscriberProjection {
    Long getId();
    String getEmail();
    String getFullName();
    String getUsername();
    String getLocation();
    String getAbout();
    String getWebsite();
    String getCountryCode();
    Long getPhone();
    String getCountry();
    String getGender();
    String getLanguage();
    String getBirthday();
    LocalDateTime getRegistrationDate();
    Long getTweetCount();
    Long getMediaTweetCount();
    Long getLikeCount();
    Long getNotificationsCount();
    boolean isActive();
    boolean isProfileCustomized();
    boolean isProfileStarted();
    boolean isMutedDirectMessages();
    boolean isPrivateProfile();
    BackgroundColorType getBackgroundColor();
    ColorSchemeType getColorScheme();
}