package com.gmail.javacoded78.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SameFollowerResponse {

    private Long id;
    private String fullName;
    private String username;
    private String avatar;
}