package com.gmail.javacoded78.latwitter.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.javacoded78.latwitter.dto.Views;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    @JsonView({Views.User.class, Views.Tweet.class})
    private Long id;

    @JsonView({Views.User.class, Views.Tweet.class})
    private String email;

    @JsonView({Views.User.class, Views.Tweet.class})
    private String fullName;

    @JsonView({Views.User.class, Views.Tweet.class})
    private String username;

    @JsonView({Views.User.class, Views.Tweet.class})
    private String location;

    @JsonView({Views.User.class, Views.Tweet.class})
    private String about;

    @JsonView({Views.User.class, Views.Tweet.class})
    private String website;

    @JsonView({Views.User.class, Views.Tweet.class})
    private boolean confirmed;

    @JsonView(Views.User.class)
    private List<TweetResponse> tweets;
}
