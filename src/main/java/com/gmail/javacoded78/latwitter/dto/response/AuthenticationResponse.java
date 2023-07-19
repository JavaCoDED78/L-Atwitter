package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private AuthUserResponse user;
    private String token;
}
