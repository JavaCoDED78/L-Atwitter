package com.gmail.javacoded78.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileTweetImageResponse {

    private Long tweetId;
    private Long imageId;
    private String src;
}