package com.gmail.javacoded78.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String fullName;
    private String about;
    private String location;
    private String website;
    private String avatar;
    private String wallpaper;
}
