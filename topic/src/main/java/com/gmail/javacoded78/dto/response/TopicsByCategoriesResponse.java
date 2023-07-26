package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.repository.projetion.TopicProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopicsByCategoriesResponse {

    private String topicCategory;
    private List<TopicProjection> topicsByCategories;
}
