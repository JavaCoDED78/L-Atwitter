package com.gmail.javacoded78.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPrincipalResponse {

    private Long id;
    private String email;
    private String activationCode;
}
