package com.gmail.javacoded78.dto.response.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatTweetUserResponse {

    private Long id;
    private String fullName;
    private String username;
    private String avatar;
}
