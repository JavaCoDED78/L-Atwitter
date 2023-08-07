package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.dto.response.lists.CommonUserResponse;
import lombok.Data;

@Data
public class ListUserResponse {
    private Long id;
    private String name;
    private String description;
    private String altWallpaper;
    private String wallpaper;
    private CommonUserResponse listOwner;

    @JsonProperty("isPrivate")
    private boolean isPrivate;

    @JsonProperty("isListPinned")
    private boolean isListPinned;
}
