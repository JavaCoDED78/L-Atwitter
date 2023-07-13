package com.gmail.javacoded78.latwitter.dto.response.chat;

import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponse {

    private Long id;
    private String text;
    private LocalDateTime date;
    private ChatTweetResponse tweet;
    private ChatParticipantResponse author;
    private ChatResponse chat;
}
