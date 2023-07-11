package com.gmail.javacoded78.latwitter.dto.response;

import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import lombok.Data;

import java.util.List;

@Data
public class TagResponse {
    private Long id;
    private String tagName;
    private Long tweetsQuantity;
    private List<TweetResponse> tweets;
}
