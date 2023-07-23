package com.gmail.javacoded78.latwitter.repository.projection.user;

import com.gmail.javacoded78.latwitter.enums.BackgroundColorType;
import com.gmail.javacoded78.latwitter.enums.ColorSchemeType;
import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;
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

    boolean isActive();

    boolean isProfileCustomized();

    boolean isProfileStarted();

    boolean isMutedDirectMessages();

    boolean isPrivateProfile();

    BackgroundColorType getBackgroundColor();

    ColorSchemeType getColorScheme();

    ImageProjection getAvatar();

    ImageProjection getWallpaper();

    @Value("#{target.pinnedTweet != null ? target.pinnedTweet.id : 0}")
    Integer getPinnedTweetId();

    @Value("#{target.followers.size()}")
    Integer getFollowersSize();

    @Value("#{target.following.size()}")
    Integer getFollowingSize();

    @Value("#{target.followerRequests.size()}")
    Integer getFollowerRequestsSize();

    @Value("#{target.unreadMessages.size()}")
    Integer getUnreadMessagesSize();
}