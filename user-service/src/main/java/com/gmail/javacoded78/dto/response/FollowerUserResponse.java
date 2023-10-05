package com.gmail.javacoded78.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowerUserResponse {

    private Long id;
    private String fullName;
    private String username;
    private String about;
    private String avatar;
}
