package com.gmail.javacoded78.latwitter.dto.response;

import com.gmail.javacoded78.latwitter.model.LinkCoverSize;
import com.gmail.javacoded78.latwitter.model.ReplyType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TweetResponseCommon {

    private Long id;
    private String text;
    private LocalDateTime dateTime;
    private LocalDateTime scheduledDate;
    private String addressedUsername;
    private Long addressedId;
    private Long addressedTweetId;
    private ReplyType replyType;
    private String link;
    private String linkTitle;
    private String linkDescription;
    private String linkCover;
    private LinkCoverSize linkCoverSize;
    private List<ImageResponse> images;
    private UserResponseCommon user;
}