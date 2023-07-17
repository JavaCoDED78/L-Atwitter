package com.gmail.javacoded78.latwitter.dto.response.chat;

import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import com.gmail.javacoded78.latwitter.model.LinkCoverSize;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatTweetResponse {

    private Long id;
    private String text;
    private LocalDateTime dateTime;
    private String link;
    private String linkTitle;
    private String linkDescription;
    private String linkCover;
    private LinkCoverSize linkCoverSize;
    private ChatUserResponse user;
    private List<ImageResponse> images;
}