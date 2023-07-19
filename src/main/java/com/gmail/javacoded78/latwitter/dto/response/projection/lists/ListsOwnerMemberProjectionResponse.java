package com.gmail.javacoded78.latwitter.dto.response.projection.lists;

import com.gmail.javacoded78.latwitter.dto.response.projection.ImageProjectionResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListsOwnerMemberProjectionResponse {

    private Long id;
    private String fullName;
    private String username;
    private String about;
    private ImageProjectionResponse avatar;
    boolean isMemberInList;
}
