package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.dto.response.tweet.TweetAdditionalInfoUserResponse;
import com.gmail.javacoded78.enums.ReplyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TweetAdditionalInfoResponse {

    private String text;
    private ReplyType replyType;
    private Long addressedTweetId;
    private TweetAdditionalInfoUserResponse user;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
}
