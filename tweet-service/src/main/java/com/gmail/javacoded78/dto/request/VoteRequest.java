package com.gmail.javacoded78.dto.request;

import lombok.Data;

@Data
public class VoteRequest {

    private Long tweetId;
    private Long pollId;
    private Long pollChoiceId;
}