package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.ChatMessage;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.repository.projection.chat.ChatMessageProjection;
import com.gmail.javacoded78.latwitter.repository.projection.chat.ChatMessagesProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT cm AS message FROM ChatMessage cm " +
            "JOIN cm.chat.participants cp " +
            "WHERE cm.chat.id = :chatId " +
            "AND cp.user.id = :userId")
    List<ChatMessagesProjection> getAllByChatId(Long chatId, Long userId);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.id = :messageId")
    ChatMessageProjection getChatMessageById(Long messageId);

    List<ChatMessage> findByTweet(Tweet tweet);
}
