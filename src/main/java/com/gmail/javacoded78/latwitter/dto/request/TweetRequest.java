package com.gmail.javacoded78.latwitter.dto.request;

import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.model.Image;
import lombok.Data;

import java.util.List;

@Data
public class TweetRequest {

    private Long id;
    private String text;
    private String addressedUsername;
    private List<Image> images;
    private List<UserRequest> likes;
    private List<UserResponse> retweets;
}
