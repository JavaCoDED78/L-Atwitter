package com.gmail.javacoded78.latwitter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Data
@AllArgsConstructor
public class HeaderResponse<T> {

    private List<T> items;
    private HttpHeaders headers;
}
