package com.gmail.javacoded78.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SuggestedTopicsRequest {

    private List<Long> topicsIds;
}
