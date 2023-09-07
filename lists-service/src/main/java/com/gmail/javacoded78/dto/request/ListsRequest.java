package com.gmail.javacoded78.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListsRequest {

    private Long id;
    private String name;
    private String description;
    private Boolean isPrivate;
    private Long listOwnerId;
    private String altWallpaper;
    private String wallpaper;
}