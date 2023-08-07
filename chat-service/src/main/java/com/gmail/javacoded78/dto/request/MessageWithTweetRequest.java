package com.gmail.javacoded78.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageWithTweetRequest {

    private String text;
    private Long tweetId;
    private List<Long> usersIds;
}
