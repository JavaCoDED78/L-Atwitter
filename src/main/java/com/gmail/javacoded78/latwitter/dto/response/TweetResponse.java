package com.gmail.javacoded78.latwitter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TweetResponse {

    private Long id;
    private String text;
    private LocalDateTime dateTime;
}
