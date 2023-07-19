package com.gmail.javacoded78.latwitter.dto.response.projection.lists;

import com.gmail.javacoded78.latwitter.dto.response.projection.ImageProjectionResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PinnedListProjectionResponse {

    private Long id;
    private String name;
    private String altWallpaper;
    private ImageProjectionResponse wallpaper;
}
