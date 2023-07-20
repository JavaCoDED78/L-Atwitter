package com.gmail.javacoded78.latwitter.dto.response.lists;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListOwnerResponse {

    private Long id;
    private String fullName;
    private String username;
    private ImageResponse avatar;

    @JsonProperty("isPrivateProfile")
    private boolean privateProfile;
}
