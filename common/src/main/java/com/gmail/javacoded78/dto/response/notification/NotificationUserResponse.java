package com.gmail.javacoded78.dto.response.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationUserResponse {

    private Long id;
    private String username;
    private String avatar;

    @JsonProperty("isFollower")
    private boolean isFollower;
}
