package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.dto.ImageResponse;
import lombok.Data;

@Data
public class SimpleListResponse {

    private Long id;
    private String name;
    private String altWallpaper;
    private ImageResponse wallpaper;

    @JsonProperty("isMemberInList")
    private boolean isMemberInList;

    @JsonProperty("isPrivate")
    private boolean isPrivate;
}
