package com.gmail.javacoded78.dto.request;

import com.gmail.javacoded78.dto.response.ImageResponse;
import com.gmail.javacoded78.dto.response.UserResponse;
import lombok.Data;

@Data
public class ListsRequest {

    private Long id;
    private String name;
    private String description;
    private Boolean isPrivate;
    private UserResponse listOwner;
    private String altWallpaper;
    private ImageResponse wallpaper;
}