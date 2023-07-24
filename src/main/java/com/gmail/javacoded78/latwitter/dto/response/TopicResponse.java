package com.gmail.javacoded78.latwitter.dto.response;

import com.gmail.javacoded78.latwitter.enums.TopicCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicResponse {

    private Long id;
    private String topicName;
    private TopicCategory topicCategory;
}
