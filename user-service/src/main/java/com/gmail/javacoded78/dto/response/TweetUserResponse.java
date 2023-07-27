package com.gmail.javacoded78.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TweetUserResponse extends TweetResponse {
    
    private List<Long> retweetsUserIds;
}
