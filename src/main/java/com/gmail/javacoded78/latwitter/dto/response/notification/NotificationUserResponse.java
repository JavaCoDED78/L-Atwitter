package com.gmail.javacoded78.latwitter.dto.response.notification;

import com.gmail.javacoded78.latwitter.dto.response.FollowerResponse;
import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NotificationUserResponse {

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
    private Long mediaTweetCount;
    private Long likeCount;
    private ImageResponse avatar;
    private ImageResponse wallpaper;
    private List<FollowerResponse> followers;
    private List<FollowerResponse> following;
}
