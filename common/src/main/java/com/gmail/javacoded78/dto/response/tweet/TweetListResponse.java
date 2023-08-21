package com.gmail.javacoded78.dto.response.tweet;

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
public class TweetListResponse {

    private Long id;
    private String name;
    private String altWallpaper;
    private String wallpaper;
    private CommonUserResponse listOwner;
    private Long membersSize;

    @JsonProperty("isPrivate")
    private boolean isPrivate;
}
