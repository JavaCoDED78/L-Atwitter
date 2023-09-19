package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TweetUserResponse extends TweetResponse {

    private List<Long> retweetsUserIds;
}
