package com.gmail.javacoded78.client.tweet;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
public class TweetPageableRequest {

    private Long userId;
    private Pageable pageable;
}