package com.gmail.javacoded78.dto.response.tweet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetImageResponse {

    private Long tweetId;
    private Long imageId;
    private String src;
}
