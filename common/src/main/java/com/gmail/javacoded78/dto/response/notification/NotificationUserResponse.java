package com.gmail.javacoded78.dto.response.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificationUserResponse {

    private Long id;
    private String username;
    private String avatar;

    @JsonProperty("isFollower")
    private boolean isFollower;
}
