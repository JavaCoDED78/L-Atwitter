package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuoteTweetResponse {

    private Long id;
    private String text;
    private UserResponse user;
    private LocalDateTime dateTime;
}
