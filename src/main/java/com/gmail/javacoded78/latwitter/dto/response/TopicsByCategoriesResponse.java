package com.gmail.javacoded78.latwitter.dto.response;

import com.gmail.javacoded78.latwitter.repository.projection.topic.TopicProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopicsByCategoriesResponse {

    private String topicCategory;
    private List<TopicProjection> topicsByCategories;
}