package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.Chat;
import com.gmail.javacoded78.repository.projection.ChatProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("""
            SELECT ch FROM Chat ch
            WHERE ch.id = :chatId
            """)
    ChatProjection getChatById(@Param("chatId") Long chatId);

    @Query("""
            SELECT ch FROM Chat ch
            LEFT JOIN ch.participants cp
            WHERE ch.id = :chatId
            AND cp.userId = :userId
            """)
    <T> Optional<T> getChatById(@Param("chatId") Long chatId, @Param("userId") Long userId, Class<T> type);

    @Query("""
            SELECT ch FROM Chat ch
            LEFT JOIN ch.participants p
            WHERE p.userId IN (:authUserId, :userId)
            GROUP BY ch
            HAVING COUNT(DISTINCT p.userId) = 2
            """)
    Chat getChatByParticipants(@Param("authUserId") Long authUserId, @Param("userId") Long userId);

    @Query("""
            SELECT ch FROM Chat ch
            LEFT JOIN ch.participants p
            WHERE p.userId = :userId
            AND p.leftChat = false
            """)
    List<ChatProjection> getChatsByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT CASE WHEN count(chatParticipant) > 0
                THEN true
                ELSE false END
            FROM Chat chat
            JOIN chat.participants chatParticipant
            WHERE chat.id = :chatId
            AND chatParticipant.userId = :userId
            """)
    boolean isChatExists(@Param("chatId") Long chatId, @Param("userId") Long userId);
}
