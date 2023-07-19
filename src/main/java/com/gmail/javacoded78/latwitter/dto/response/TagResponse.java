package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResponse {

    private Long id;
    private String tagName;
    private Long tweetsQuantity;
}
