package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private UserResponse user;
    private String token;
}
