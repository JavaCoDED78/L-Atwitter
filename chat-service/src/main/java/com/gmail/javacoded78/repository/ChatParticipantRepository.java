package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {

    @Query("""
            SELECT cp.userId
            FROM ChatParticipant cp
            WHERE cp.chat.id = :chatId
            """)
    List<Long> getChatParticipantIds(@Param("chatId") Long chatId);

    @Modifying
    @Query("""
            UPDATE ChatParticipant cp
            SET cp.leftChat = false
            WHERE cp.userId = :userId
            AND cp.chat.id = :chatId
            """)
    void updateParticipantWhoLeftChat(@Param("userId") Long userId, @Param("chatId") Long chatId);

    @Query("""
            SELECT cp.userId
            FROM ChatParticipant cp
            WHERE cp.id = :participantId
            AND cp.chat.id = :chatId
            """)
    Optional<Long> getParticipantUserId(@Param("participantId") Long participantId, @Param("chatId") Long chatId);

    @Query("""
            SELECT cp.userId
            FROM ChatParticipant cp
            WHERE cp.chat.id = :chatId
            GROUP BY cp.userId
            HAVING cp.userId <> :authUserId
            """)
    Long getChatParticipantId(@Param("authUserId") Long authUserId, @Param("chatId") Long chatId);

    @Modifying
    @Query("""
            UPDATE ChatParticipant cp
            SET cp.leftChat = true
            WHERE cp.id = :participantId
            AND cp.chat.id = :chatId
            """)
    int leaveFromConversation(@Param("participantId") Long participantId, @Param("chatId") Long chatId);
}