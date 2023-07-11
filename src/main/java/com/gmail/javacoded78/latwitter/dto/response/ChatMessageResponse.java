package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponse {

    private Long id;
    private String text;
    private LocalDateTime date;
    private ChatParticipantResponse author;
    private ChatResponse chat;
}
