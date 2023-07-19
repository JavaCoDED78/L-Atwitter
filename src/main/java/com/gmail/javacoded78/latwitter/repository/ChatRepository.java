package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Chat;
import com.gmail.javacoded78.latwitter.repository.projection.chat.ChatProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c as chat FROM Chat c WHERE c.id = :chatId")
    ChatProjection getChatById(Long chatId);

    @Query("SELECT chat FROM Chat chat " +
            "JOIN chat.participants chatParticipant " +
            "WHERE chat.id = :chatId " +
            "AND chatParticipant.user.id = :userId")
    Optional<Chat> getChatByUserId(Long chatId, Long userId);
}
