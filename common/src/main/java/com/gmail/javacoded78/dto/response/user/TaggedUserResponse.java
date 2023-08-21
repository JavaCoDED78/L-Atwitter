package com.gmail.javacoded78.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaggedUserResponse {

    private Long id;
    private String fullName;
}