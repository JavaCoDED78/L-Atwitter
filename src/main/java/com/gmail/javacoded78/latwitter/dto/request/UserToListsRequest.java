package com.gmail.javacoded78.latwitter.dto.request;

import com.gmail.javacoded78.latwitter.dto.response.ListsResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserToListsRequest {

    private Long userId;
    private List<ListsResponse> lists;
}
