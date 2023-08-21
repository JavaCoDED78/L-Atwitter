package com.gmail.javacoded78.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonUserResponse {

    private Long id;
    private String fullName;
    private String username;
    private String avatar;

    @JsonProperty("isPrivateProfile")
    private boolean privateProfile;
}
