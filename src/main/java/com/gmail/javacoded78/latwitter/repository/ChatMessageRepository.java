package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.ChatMessage;
import com.gmail.javacoded78.latwitter.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> getAllByChatId(Long chatId);

    List<ChatMessage> findByTweet(Tweet tweet);
}
