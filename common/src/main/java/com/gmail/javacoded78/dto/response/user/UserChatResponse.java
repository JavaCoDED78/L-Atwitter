package com.gmail.javacoded78.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChatResponse extends UserResponse {

    @JsonProperty("isUserChatParticipant")
    private boolean isUserChatParticipant;
}
