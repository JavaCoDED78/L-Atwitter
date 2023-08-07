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

    @Query("SELECT chat FROM Chat chat WHERE chat.id = :chatId")
    ChatProjection getChatById(@Param("chatId") Long chatId);

    @Query("SELECT chat FROM Chat chat " +
            "LEFT JOIN chat.participants chatParticipant " +
            "WHERE chat.id = :chatId " +
            "AND chatParticipant.userId = :userId")
    <T> Optional<T> getChatById(@Param("chatId") Long chatId, @Param("userId") Long userId, Class<T> type);

    @Query("SELECT chat FROM Chat chat " +
            "LEFT JOIN chat.participants participant " +
            "WHERE participant.userId IN (:authUserId, :userId)  " +
            "GROUP BY chat " +
            "HAVING COUNT(DISTINCT participant.userId) = 2")
    Chat getChatByParticipants(@Param("authUserId") Long authUserId, @Param("userId") Long userId);

    @Query("SELECT chat FROM Chat chat " +
            "LEFT JOIN chat.participants participant " +
            "WHERE participant.userId = :userId " +
            "AND participant.leftChat = false")
    List<ChatProjection> getChatsByUserId(@Param("userId") Long userId);

    @Query("SELECT CASE WHEN count(chatParticipant) > 0 THEN true ELSE false END FROM Chat chat " +
            "JOIN chat.participants chatParticipant " +
            "WHERE chat.id = :chatId " +
            "AND chatParticipant.userId = :userId")
    boolean isChatExists(@Param("chatId") Long chatId, @Param("userId") Long userId);
}
