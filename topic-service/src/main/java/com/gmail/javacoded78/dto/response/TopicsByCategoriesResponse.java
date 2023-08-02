package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.repository.projetion.TopicProjection;
import lombok.Data;

import java.util.List;

@Data
public class TopicsByCategoriesResponse {

    private String topicCategory;
    private List<TopicProjection> topicsByCategories;
}
