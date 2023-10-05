package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.enums.BackgroundColorType;
import com.gmail.javacoded78.enums.ColorSchemeType;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface AuthUserProjection {
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
    Long getMentionsCount();
    boolean isActive();
    boolean isProfileCustomized();
    boolean isProfileStarted();
    boolean isMutedDirectMessages();
    boolean isPrivateProfile();
    BackgroundColorType getBackgroundColor();
    ColorSchemeType getColorScheme();
    String getAvatar();
    String getWallpaper();
    Long getPinnedTweetId();
    Long getUnreadMessagesCount();

    @Value("#{target.followers.size()}")
    Long getFollowersSize();

    @Value("#{target.following.size()}")
    Long getFollowingSize();

    @Value("#{target.followerRequests.size()}")
    Long getFollowerRequestsSize();
}

