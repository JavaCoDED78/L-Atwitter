package com.gmail.javacoded78.latwitter.dto.response.tweet;

import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetUserResponse {

    private Long id;
    private String email;
    private String fullName;
    private String username;
    private ImageResponse avatar;
}
