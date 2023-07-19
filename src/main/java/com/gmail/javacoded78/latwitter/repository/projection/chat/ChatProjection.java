package com.gmail.javacoded78.latwitter.repository.projection.chat;

import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatProjection {

    Long getId();

    LocalDateTime getCreationDate();

    List<NestedChatParticipantProjection> getParticipants();

    interface NestedChatParticipantProjection {

        ChatUserProjection getUser();

        interface ChatUserProjection {

            Long getId();

            String getFullName();

            String getUsername();

            ImageProjection getAvatar();
        }
    }
}
