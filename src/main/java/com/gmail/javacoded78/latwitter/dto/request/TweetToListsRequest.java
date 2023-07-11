package com.gmail.javacoded78.latwitter.dto.request;

import com.gmail.javacoded78.latwitter.dto.response.ListsResponse;
import lombok.Data;

import java.util.List;

@Data
public class TweetToListsRequest {

    private Long tweetId;
    private List<ListsResponse> lists;
}