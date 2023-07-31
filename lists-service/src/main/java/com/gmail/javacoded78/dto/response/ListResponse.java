package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.dto.lists.ListOwnerResponse;
import lombok.Data;

@Data
public class ListResponse {

    private Long id;
    private String name;
    private String description;
    private String altWallpaper;
    private ListsWallpaperResponse wallpaper;
    private ListOwnerResponse listOwner;

    @JsonProperty("isFollower")
    private boolean isFollower;
}