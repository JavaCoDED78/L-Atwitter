package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.dto.ImageResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime pinnedDate;
    private String altWallpaper;
    private ImageResponse wallpaper;
    private ListOwnerResponse listOwner;

    @JsonProperty("isFollower")
    private boolean isFollower;
}