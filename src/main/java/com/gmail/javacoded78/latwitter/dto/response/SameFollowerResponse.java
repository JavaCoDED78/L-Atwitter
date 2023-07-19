package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SameFollowerResponse {

    private Long id;
    private String fullName;
    private Map<String, Object> avatar;
}