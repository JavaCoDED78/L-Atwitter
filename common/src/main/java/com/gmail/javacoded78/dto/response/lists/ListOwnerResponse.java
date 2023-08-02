package com.gmail.javacoded78.dto.response.lists;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ListOwnerResponse {

    private Long id;
    private String fullName;
    private String username;
    private String avatar;

    @JsonProperty("isPrivateProfile")
    private boolean privateProfile;
}
