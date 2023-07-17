package com.gmail.javacoded78.latwitter.dto.response.chat;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatResponse {

    private Long id;
    private LocalDateTime creationDate;
    private List<ChatParticipantResponse> participants;
}
