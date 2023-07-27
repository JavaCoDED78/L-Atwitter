package com.gmail.javacoded78.dto.response.lists;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PinnedListResponse {

    private Long id;
    private String name;
    private LocalDateTime pinnedDate;
    private String altWallpaper;
    private ImageResponse wallpaper;

    @JsonProperty("isPrivate")
    private boolean isPrivate;
}
