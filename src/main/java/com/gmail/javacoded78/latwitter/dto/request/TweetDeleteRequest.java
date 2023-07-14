package com.gmail.javacoded78.latwitter.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TweetDeleteRequest {

    private List<Long> tweetsIds;
}
