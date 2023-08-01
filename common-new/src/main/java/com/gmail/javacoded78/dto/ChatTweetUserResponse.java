package com.gmail.javacoded78.dto;

import lombok.Data;

@Data
public class ChatTweetUserResponse {

    private Long id;
    private String fullName;
    private String username;
    private ImageResponse avatar;
}
