package com.gmail.javacoded78.latwitter.dto.request;

import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RetweetRequest {

    private Long id;
    private LocalDateTime retweetDate;
    private Tweet tweet;
    private List<User> users;
}
