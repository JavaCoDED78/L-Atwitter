package com.gmail.javacoded78.latwitter.util;

import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import lombok.Data;

import java.util.List;

@Data
public class ListsRequest {

    private Long id;
    private List<UserResponse> members;
}
