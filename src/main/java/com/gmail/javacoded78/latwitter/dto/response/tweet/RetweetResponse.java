package com.gmail.javacoded78.latwitter.dto.response.tweet;

import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RetweetResponse {

    private Long id;
    private LocalDateTime retweetDate;
    private UserResponse user;
}
