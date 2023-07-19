package com.gmail.javacoded78.latwitter.dto.response.tweet;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Data
@AllArgsConstructor
public class TweetHeaderResponse {

    private List<TweetResponse> tweets;
    private HttpHeaders headers;
}
