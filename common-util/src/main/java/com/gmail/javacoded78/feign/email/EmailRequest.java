package com.gmail.javacoded78.feign.email;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class EmailRequest {

    private String to;
    private String subject;
    private String template;
    private Map<String, Object> attributes;
}
