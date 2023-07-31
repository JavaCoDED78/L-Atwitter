package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.dto.lists.ListOwnerResponse;
import lombok.Data;

@Data
public class BaseListResponse {
    private Long id;
    private String name;
    private String description;
    private String altWallpaper;
    private String wallpaper;
    private ListOwnerResponse listOwner;
    private Long membersSize;
    private Long followersSize;

    @JsonProperty("isPrivate")
    private boolean isPrivate;

    @JsonProperty("isFollower")
    private boolean isFollower;
}
