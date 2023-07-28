package com.gmail.javacoded78.common.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private AuthUserResponse user;
    private String token;
}
