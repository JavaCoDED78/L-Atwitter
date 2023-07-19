package com.gmail.javacoded78.latwitter.dto.response.lists;

import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListsOwnerMemberResponse {

    private Long id;
    private String fullName;
    private String username;
    private String about;
    private ImageResponse avatar;
    boolean isMemberInList;
}
