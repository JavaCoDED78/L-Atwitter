package com.gmail.javacoded78.latwitter.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SuggestedTopicsRequest {

    private List<Long> topicsIds;
}