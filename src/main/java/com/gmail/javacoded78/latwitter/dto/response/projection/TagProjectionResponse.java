package com.gmail.javacoded78.latwitter.dto.response.projection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagProjectionResponse {

    private Long id;
    private String tagName;
    private Long tweetsQuantity;
}
