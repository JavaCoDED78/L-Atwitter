package com.gmail.javacoded78.latwitter.dto.response.tweet;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TweetUserResponse extends TweetResponse {
    
    private List<Long> retweetsUserIds;
}
