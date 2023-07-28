package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.dto.ImageResponse;
import lombok.Data;

@Data
public class ListOwnerResponse {

    private Long id;
    private String fullName;
    private String username;
    private ImageResponse avatar;

    @JsonProperty("isPrivateProfile")
    private boolean privateProfile;
}
