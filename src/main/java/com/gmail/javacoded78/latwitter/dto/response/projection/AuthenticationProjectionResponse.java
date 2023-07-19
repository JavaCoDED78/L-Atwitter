package com.gmail.javacoded78.latwitter.dto.response.projection;

import lombok.Data;

@Data
public class AuthenticationProjectionResponse {

    private AuthUserProjectionResponse user;
    private String token;
}
