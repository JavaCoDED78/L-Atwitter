package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.dto.ChatUserParticipantResponse;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatProjection {
    Long getId();
    LocalDateTime getCreationDate();
    List<ChatParticipantProjection> getParticipants();

    interface ChatParticipantProjection {
        Long getId();
        Long getUserId();

        @Value("#{@chatServiceImpl.getChatParticipant(target.userId)}")
        ChatUserParticipantResponse getUser();
        boolean getLeftChat();
    }
}