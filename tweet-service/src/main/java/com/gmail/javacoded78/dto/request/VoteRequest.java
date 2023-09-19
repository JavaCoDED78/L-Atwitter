package com.gmail.javacoded78.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteRequest {

    private Long tweetId;
    private Long pollId;
    private Long pollChoiceId;
}
