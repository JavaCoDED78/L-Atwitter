package com.gmail.javacoded78.dto.request;
;
import com.gmail.javacoded78.common.dto.ImageResponse;
import com.gmail.javacoded78.common.dto.UserResponse;
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