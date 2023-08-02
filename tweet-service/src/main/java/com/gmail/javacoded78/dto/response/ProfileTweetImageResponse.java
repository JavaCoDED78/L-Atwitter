package com.gmail.javacoded78.dto.response;

import lombok.Data;

@Data
public class ProfileTweetImageResponse {

    private Long tweetId;
    private Long imageId;
    private String src;
}