package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.enums.TopicCategory;
import com.gmail.javacoded78.repository.projetion.TopicProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicsByCategoriesResponse {

    private TopicCategory topicCategory;
    private List<TopicProjection> topicsByCategories;
}
