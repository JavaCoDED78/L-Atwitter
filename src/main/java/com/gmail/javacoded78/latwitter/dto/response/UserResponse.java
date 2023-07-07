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
    private ImageResponse avatar;
    private ImageResponse wallpaper;
    private boolean confirmed;
    private List<FollowerResponse> followers;
    private List<FollowerResponse> following;
}
