package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {

    private Long id;
    private List<ChatParticipantResponse> participants;
}