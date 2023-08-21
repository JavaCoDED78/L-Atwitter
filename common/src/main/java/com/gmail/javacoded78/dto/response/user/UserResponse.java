package com.gmail.javacoded78.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponse {

    private Long id;
    private String fullName;
    private String username;
    private String about;
    private String avatar;

    @JsonProperty("isPrivateProfile")
    private boolean privateProfile;

    @JsonProperty("isMutedDirectMessages")
    private boolean mutedDirectMessages;

    @JsonProperty("isUserBlocked")
    private boolean isUserBlocked;

    @JsonProperty("isMyProfileBlocked")
    private boolean isMyProfileBlocked;

    @JsonProperty("isWaitingForApprove")
    private boolean isWaitingForApprove;

    @JsonProperty("isFollower")
    private boolean isFollower;
}
