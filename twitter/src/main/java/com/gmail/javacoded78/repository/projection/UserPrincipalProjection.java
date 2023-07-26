package com.gmail.javacoded78.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPrincipalProjection {

    private Long id;
    private String email;
    private String password;
    private String activationCode;
}
