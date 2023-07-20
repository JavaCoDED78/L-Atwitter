package com.gmail.javacoded78.latwitter.dto.response.tweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTweetResponse {

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
