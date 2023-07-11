package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

@Data
public class ChatParticipantResponse {

    private Long id;
    private String email;
    private String fullName;
    private String username;
    private ImageResponse avatar;
}

