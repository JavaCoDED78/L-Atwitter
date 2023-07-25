package com.gmail.javacoded78.latwitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.latwitter.enums.TopicCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicResponse {

    private Long id;
    private String topicName;
    private TopicCategory topicCategory;

    @JsonProperty("isTopicFollowed")
    private boolean isTopicFollowed;

    @JsonProperty("isTopicNotInterested")
    private boolean isTopicNotInterested;
}