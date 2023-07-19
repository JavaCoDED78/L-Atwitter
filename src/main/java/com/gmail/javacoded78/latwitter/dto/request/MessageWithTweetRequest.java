package com.gmail.javacoded78.latwitter.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class MessageWithTweetRequest {

    private String text;
    private Long tweetId;
    private List<Long> usersIds;
}
