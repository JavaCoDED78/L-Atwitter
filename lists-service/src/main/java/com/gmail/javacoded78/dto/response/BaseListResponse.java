package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.common.dto.ImageResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseListResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime pinnedDate;
    private String altWallpaper;
    private ImageResponse wallpaper;
    private ListOwnerResponse listOwner;
    private Integer membersSize;
    private Integer followersSize;

    @JsonProperty("isPrivate")
    private boolean isPrivate;

    @JsonProperty("isFollower")
    private boolean isFollower;
}
