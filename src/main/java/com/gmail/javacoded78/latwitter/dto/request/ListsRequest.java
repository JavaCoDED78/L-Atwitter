package com.gmail.javacoded78.latwitter.dto.request;

import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import lombok.Data;

@Data
public class ListsRequest {

    private String name;
    private String description;
    private boolean isPrivate;
    private String altWallpaper;
    private ImageResponse wallpaper;
}