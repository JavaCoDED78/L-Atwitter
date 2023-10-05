package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResultResponse {

    private String text;
    private Long tweetCount;
    private List<String> tags;
    private List<CommonUserResponse> users;
}
