package com.gmail.javacoded78.latwitter.dto.response.lists;

import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ListResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime pinnedDate;
    private String altWallpaper;
    private ImageResponse wallpaper;
    private ListOwnerResponse listOwner;
    private boolean follower;
}
