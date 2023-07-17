package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class FollowerResponse {

    private Long id;
    private String email;
    private String fullName;
    private String username;
    private String location;
    private String about;
    private String website;
    private boolean privateProfile;
    private ImageResponse avatar;
    private ImageResponse wallpaper;
    private boolean confirmed;
    private List<CommonUserResponse> userBlockedList;
    private List<CommonUserResponse> followers;
    private List<CommonUserResponse> following;
}
