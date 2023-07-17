package com.gmail.javacoded78.latwitter.dto.response.chat;

import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import lombok.Data;

@Data
public class ChatParticipantResponse {

    private Long id;
    private boolean leftChat;
    private ChatResponse chat;
    private ChatUserResponse user;
}

