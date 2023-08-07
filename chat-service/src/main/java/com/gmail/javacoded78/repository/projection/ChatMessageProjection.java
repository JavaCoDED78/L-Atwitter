package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.dto.response.chat.ChatTweetResponse;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface ChatMessageProjection {
    Long getId();
    String getText();
    LocalDateTime getDate();
    Long getAuthorId();
    Long getTweetId();
    @Value("#{target.tweetId == null ? null : @chatServiceHelper.getChatTweet(target.tweetId)}")
    ChatTweetResponse getTweet();
    ChatProjection getChat();

    interface ChatProjection {
        Long getId();
    }
}