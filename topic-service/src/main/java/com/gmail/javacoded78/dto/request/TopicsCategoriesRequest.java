package com.gmail.javacoded78.dto.request;

import com.gmail.javacoded78.common.enums.TopicCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopicsCategoriesRequest {

    private List<TopicCategory> categories;
}
