package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseListResponse {
    private Long id;
    private String listName;
    private String description;
    private String altWallpaper;
    private String wallpaper;
    private CommonUserResponse listOwner;
    private Long membersSize;
    private Long followersSize;

    @JsonProperty("isPrivate")
    private boolean isPrivate;

    @JsonProperty("isFollower")
    private boolean isFollower;
}
