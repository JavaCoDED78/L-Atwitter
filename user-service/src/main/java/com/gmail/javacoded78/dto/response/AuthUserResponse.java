package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.enums.BackgroundColorType;
import com.gmail.javacoded78.enums.ColorSchemeType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthUserResponse {

    private Long id;
    private String email;
    private String fullName;
    private String username;
    private String location;
    private String about;
    private String website;
    private String countryCode;
    private Long phone;
    private String country;
    private String gender;
    private String language;
    private String birthday;
    private LocalDateTime registrationDate;
    private Long tweetCount;
    private Long mediaTweetCount;
    private Long likeCount;
    private Long notificationsCount;
    private boolean active;
    private boolean profileCustomized;
    private boolean profileStarted;
    @JsonProperty("isMutedDirectMessages")
    private boolean mutedDirectMessages;
    @JsonProperty("isPrivateProfile")
    private boolean privateProfile;
    private BackgroundColorType backgroundColor;
    private ColorSchemeType colorScheme;
    private String avatar;
    private String wallpaper;
    private Long pinnedTweetId;
    private Long followersSize;
    private Long followingSize;
    private Long followerRequestsSize;
    private Long unreadMessagesSize;
}
