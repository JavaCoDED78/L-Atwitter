package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.ChatMessage;
import com.gmail.javacoded78.repository.projection.ChatMessageProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("""
            SELECT cm FROM ChatMessage cm
            WHERE cm.chat.id = :chatId
            ORDER BY cm.date ASC
            """)
    List<ChatMessageProjection> getChatMessages(@Param("chatId") Long chatId);

    @Modifying
    @Query("""
            UPDATE ChatMessage cm
            SET cm.unread = false
            WHERE cm.chat.id = :chatId
            AND cm.authorId <> :userId
            """)
    void readChatMessages(@Param("chatId") Long chatId, @Param("userId") Long userId);

    @Query("""
            SELECT COUNT(cm) FROM ChatMessage cm
            WHERE cm.chat.id IN (
               SELECT chat.id FROM Chat chat
               LEFT JOIN chat.participants participant
               WHERE participant.userId = :userId
               AND participant.leftChat = false)
            AND cm.unread = true
            AND cm.authorId <> :userId
            """)
    Long getUnreadMessagesCount(@Param("userId") Long userId);

    @Query("""
            SELECT cm FROM ChatMessage cm
            WHERE cm.id = :messageId
            """)
    Optional<ChatMessageProjection> getChatMessageById(@Param("messageId") Long messageId);
}
