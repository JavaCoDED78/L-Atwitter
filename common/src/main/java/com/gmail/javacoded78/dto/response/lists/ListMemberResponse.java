package com.gmail.javacoded78.dto.response.lists;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListMemberResponse {

    private Long id;
    private String fullName;
    private String username;
    private String about;
    private String avatar;

    @JsonProperty("isMemberInList")
    private boolean isMemberInList;

    @JsonProperty("isPrivateProfile")
    private boolean isPrivateProfile;

    public ListMemberResponse(boolean isMemberInList) {
        this.isMemberInList = isMemberInList;
    }
}
