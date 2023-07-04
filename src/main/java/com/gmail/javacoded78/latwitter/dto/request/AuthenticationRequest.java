package com.gmail.javacoded78.latwitter.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;
    private String password;
}
