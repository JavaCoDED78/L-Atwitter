package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.Chat;
import com.gmail.javacoded78.repository.projection.chat.ChatProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c WHERE c.id = :chatId")
    ChatProjection getChatById(Long chatId);

    @Query("SELECT chat FROM Chat chat " +
            "JOIN chat.participants chatParticipant " +
            "WHERE chat.id = :chatId " +
            "AND chatParticipant.user.id = :userId")
    Optional<ChatProjection> getChatById(Long chatId, Long userId);

    @Query("SELECT CASE WHEN count(chatParticipant) > 0 THEN true ELSE false END " +
            "FROM Chat chat " +
            "JOIN chat.participants chatParticipant " +
            "WHERE chat.id = :chatId " +
            "AND chatParticipant.user.id = :userId")
    boolean getChatByUserId(Long chatId, Long userId);
}
