package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.ChatParticipant;
import com.gmail.javacoded78.latwitter.repository.projection.chat.ChatParticipantsProjection;
import com.gmail.javacoded78.latwitter.repository.projection.user.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {

    @Query("SELECT cp as participant FROM User u LEFT JOIN u.chats cp WHERE u.id = :userId")
    List<ChatParticipantsProjection> getChatParticipants(Long userId);

    @Query("SELECT user FROM User user " +
            "LEFT JOIN user.chats participant " +
            "LEFT JOIN participant.chat chat " +
            "WHERE chat.id = :chatId " +
            "AND participant.id = :participantId")
    Optional<UserProjection> getChatParticipant(Long participantId, Long chatId);

    @Modifying
    @Transactional
    @Query("UPDATE ChatParticipant chatParticipant " +
            "SET chatParticipant.leftChat = true " +
            "WHERE chatParticipant.id = :participantId " +
            "AND chatParticipant.chat.id = :chatId")
    int leaveFromConversation(Long participantId, Long chatId);
}
