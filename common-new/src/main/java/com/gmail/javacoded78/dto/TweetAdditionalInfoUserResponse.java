package com.gmail.javacoded78.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TweetAdditionalInfoUserResponse {

    private Long id;
    private String fullName;
    private String username;

    @JsonProperty("isFollower")
    private boolean isFollower;

    @JsonProperty("isMyProfileBlocked")
    private boolean isMyProfileBlocked;

    @JsonProperty("isUserBlocked")
    private boolean isUserBlocked;

    @JsonProperty("isUserMuted")
    private boolean isUserMuted;
}
