package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookmarkResponse {

    private Long id;
    private LocalDateTime bookmarkDate;
    private UserResponseCommon user;
    private TweetResponseCommon tweet;
}
