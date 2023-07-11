package com.gmail.javacoded78.latwitter.dto.request;

import lombok.Data;

@Data
public class VoteRequest {

    private Long tweetId;
    private Long pollChoiceId;
}
