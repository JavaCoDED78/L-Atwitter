package com.gmail.javacoded78.latwitter.dto.request;

import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import lombok.Data;

@Data
public class ListsRequest {

    private Long id;
    private String name;
    private String description;
    private boolean isPrivate;
    private UserResponse listOwner;
    private String altWallpaper;
    private ImageResponse wallpaper;
}