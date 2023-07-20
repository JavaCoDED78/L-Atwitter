package com.gmail.javacoded78.latwitter.dto.response.notification;

import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationUserResponse {

    private Long id;
    private String username;
    private String fullName;
    private ImageResponse avatar;
    private boolean isFollower;
}
