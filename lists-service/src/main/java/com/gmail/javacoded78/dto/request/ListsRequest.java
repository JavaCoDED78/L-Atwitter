package com.gmail.javacoded78.dto.request;

import com.gmail.javacoded78.dto.lists.ListOwnerResponse;
import com.gmail.javacoded78.dto.response.ListsWallpaperResponse;
import lombok.Data;

@Data
public class ListsRequest {

    private Long id;
    private String name;
    private String description;
    private Boolean isPrivate;
    private Long listOwnerId;
    private String altWallpaper;
    private String wallpaper;
}