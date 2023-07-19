package com.gmail.javacoded78.latwitter.dto.response.projection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockedUserProjectionResponse {

    private Long id;
    private String fullName;
    private String username;
    private String about;
    private ImageProjectionResponse avatar;
    private boolean isPrivateProfile;
    private boolean isUserBlocked;
}
