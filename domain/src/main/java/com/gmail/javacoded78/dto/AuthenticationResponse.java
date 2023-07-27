package com.gmail.javacoded78.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private AuthUserResponse user;
    private String token;
}
