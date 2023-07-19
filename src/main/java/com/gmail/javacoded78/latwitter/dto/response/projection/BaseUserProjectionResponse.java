package com.gmail.javacoded78.latwitter.dto.response.projection;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class BaseUserProjectionResponse {

    private Long id;
    private String fullName;
    private String username;
    private String about;
    private Map<String, Object> avatar;
    private boolean isPrivateProfile;
    private boolean isUserBlocked;
    private boolean isMyProfileBlocked;
    private boolean isWaitingForApprove;
    private boolean isFollower;
}