package com.gmail.javacoded78.latwitter.dto.request;

import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import lombok.Data;

import java.util.List;

@Data
public class MessageWithTweetRequest {

    private String text;
    private Tweet tweet;
    private List<User> users;
}
