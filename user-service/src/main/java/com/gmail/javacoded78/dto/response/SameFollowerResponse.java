package com.gmail.javacoded78.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SameFollowerResponse {

    private Long id;
    private String fullName;
    private String username;
    private Map<String, Object> avatar;
}