package com.gmail.javacoded78.dto.response.tweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.enums.LinkCoverSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteTweetResponse {

    private Long id;
    private String text;
    private LocalDateTime dateTime;
    private String link;
    private String linkTitle;
    private String linkDescription;
    private String linkCover;
    private LinkCoverSize linkCoverSize;
    private TweetAuthorResponse user;

    @JsonProperty("isDeleted")
    private boolean isDeleted;
}
