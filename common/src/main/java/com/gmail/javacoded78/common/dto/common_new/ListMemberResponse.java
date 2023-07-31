package com.gmail.javacoded78.common.dto.common_new;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.common.dto.ImageResponse;
import lombok.Data;

@Data
public class ListMemberResponse {

    private Long id;
    private String fullName;
    private String username;
    private String about;
    private ImageResponse avatar;

    @JsonProperty("isMemberInList")
    private boolean isMemberInList;

    @JsonProperty("isPrivateProfile")
    private boolean isPrivateProfile;
}
