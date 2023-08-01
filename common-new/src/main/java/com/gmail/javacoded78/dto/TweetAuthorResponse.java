package com.gmail.javacoded78.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TweetAuthorResponse {
    private Long id;
    private String email;
    private String fullName;
    private String username;
    private ImageResponse avatar;

    @JsonProperty("isPrivateProfile")
    private boolean isPrivateProfile;

    @JsonProperty("isFollower")
    private boolean isFollower;

    @JsonProperty("isMyProfileBlocked")
    private boolean isMyProfileBlocked;

    @JsonProperty("isUserBlocked")
    private boolean isUserBlocked;

    @JsonProperty("isUserMuted")
    private boolean isUserMuted;
}