package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponse {

    private Long id;
    private String email;
    private String fullName;
    private String username;
    private String location;
    private String about;
    private String website;
    private String birthday;
    private LocalDateTime registrationDate;
    private Long tweetCount;
    private Long notificationsCount;
    private CommonTweetResponse pinnedTweet;
    private List<BookmarkResponse> bookmarks;
    private ImageResponse avatar;
    private ImageResponse wallpaper;
    private boolean profileCustomized;
    private boolean profileStarted;
    private List<ChatMessageResponse> unreadMessages;
    private List<FollowerResponse> followers;
    private List<FollowerResponse> following;
}
