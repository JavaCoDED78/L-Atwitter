package com.gmail.javacoded78.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TagResponse {

    private Long id;
    private String tagName;
    private Long tweetsQuantity;
}
