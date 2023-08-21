package com.gmail.javacoded78.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChatResponse extends UserResponse {

    @JsonProperty("isUserChatParticipant")
    private boolean isUserChatParticipant;
}
