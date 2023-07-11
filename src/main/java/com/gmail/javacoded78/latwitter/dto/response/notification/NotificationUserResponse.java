package com.gmail.javacoded78.latwitter.dto.response.notification;

import com.gmail.javacoded78.latwitter.dto.response.FollowerResponse;
import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import lombok.Data;

import java.util.List;

@Data
public class NotificationUserResponse {

    private Long id;
    private String email;
    private String fullName;
    private String username;
    private String about;
    private ImageResponse avatar;
    private List<FollowerResponse> followers;
    private List<FollowerResponse> following;
}
