package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PollChoiceResponse {

    private Long id;
    private String choice;
    private List<UserResponse> votedUser;
}
