package com.gmail.javacoded78.latwitter.dto.response.chat;

import lombok.Data;

@Data
public class ChatParticipantResponse {

    private Long id;
    private boolean leftChat;
    private ChatUserResponse user;
}

