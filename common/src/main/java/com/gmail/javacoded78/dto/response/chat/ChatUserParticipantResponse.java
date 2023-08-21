package com.gmail.javacoded78.dto.response.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatUserParticipantResponse {

    private Long id;
    private String fullName;
    private String username;
    private String avatar;

    @JsonProperty("isMutedDirectMessages")
    private boolean isMutedDirectMessages;

    @JsonProperty("isUserBlocked")
    private boolean isUserBlocked;

    @JsonProperty("isMyProfileBlocked")
    private boolean isMyProfileBlocked;
}
