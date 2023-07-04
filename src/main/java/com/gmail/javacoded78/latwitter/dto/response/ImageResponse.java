package com.gmail.javacoded78.latwitter.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.javacoded78.latwitter.dto.Views;
import lombok.Data;

@Data
public class ImageResponse {

    @JsonView({Views.User.class, Views.Tweet.class})
    private Long id;

    @JsonView({Views.User.class, Views.Tweet.class})
    private String src;
}
