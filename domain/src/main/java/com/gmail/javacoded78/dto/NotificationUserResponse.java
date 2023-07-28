package com.gmail.javacoded78.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationUserResponse {

    private Long id;
    private String username;
    private String fullName;
    private ImageResponse avatar;

    @JsonProperty("isFollower")
    private boolean isFollower;
}
