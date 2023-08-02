package com.gmail.javacoded78.dto.request;

import com.gmail.javacoded78.dto.ImageResponse;
import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String about;
    private String location;
    private String website;
    private String avatar;
    private String wallpaper;
}
