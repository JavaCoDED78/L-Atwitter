package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ListsResponse {

    private Long id;
    private String name;
    private String description;
    private boolean isPrivate;
    private String altWallpaper;
    private ImageResponse wallpaper;
    private UserResponse listOwner;
    private List<TweetResponse> tweets;
    private List<UserResponse> members;
    private List<UserResponse> followers;
}