package com.gmail.javacoded78.common.dto.common_new;

import com.gmail.javacoded78.common.dto.ImageResponse;
import lombok.Data;

@Data
public class ChatTweetUserResponse {

    private Long id;
    private String fullName;
    private String username;
    private ImageResponse avatar;
}
