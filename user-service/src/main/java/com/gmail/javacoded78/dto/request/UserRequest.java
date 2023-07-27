package com.gmail.javacoded78.dto.request;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String about;
    private String location;
    private String website;
    private ImageResponse avatar;
    private ImageResponse wallpaper;
}
