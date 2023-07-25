package com.gmail.javacoded78.latwitter.dto.response;

import com.gmail.javacoded78.latwitter.repository.projection.TopicByCategoryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopicsByCategoriesResponse {

    private String topicCategory;
    private List<TopicByCategoryProjection> topicsByCategories;
}
