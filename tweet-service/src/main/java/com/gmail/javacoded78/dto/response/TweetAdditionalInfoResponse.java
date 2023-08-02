package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.dto.TweetAdditionalInfoUserResponse;
import com.gmail.javacoded78.enums.ReplyType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TweetAdditionalInfoResponse {

    private String text;
    private ReplyType replyType;
    private Long addressedTweetId;
    private TweetAdditionalInfoUserResponse user;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
}
