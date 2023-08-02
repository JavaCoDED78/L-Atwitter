package com.gmail.javacoded78.dto.request;

import com.gmail.javacoded78.enums.TopicCategory;
import lombok.Data;

import java.util.List;

@Data
public class TopicsCategoriesRequest {

    private List<TopicCategory> categories;
}
