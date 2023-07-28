package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.common.dto.ImageResponse;
import lombok.Data;

@Data
public class ListsOwnerMemberResponse {

    private Long id;
    private String fullName;
    private String username;
    private String about;
    private ImageResponse avatar;

    @JsonProperty("isMemberInList")
    private boolean isMemberInList;

    @JsonProperty("isPrivateProfile")
    private boolean privateProfile;
}
