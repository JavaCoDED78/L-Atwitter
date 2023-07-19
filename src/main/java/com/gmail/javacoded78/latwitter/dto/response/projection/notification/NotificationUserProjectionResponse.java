package com.gmail.javacoded78.latwitter.dto.response.projection.notification;

import com.gmail.javacoded78.latwitter.dto.response.projection.ImageProjectionResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationUserProjectionResponse {

    private Long id;
    private String username;
    private ImageProjectionResponse avatar;
}
