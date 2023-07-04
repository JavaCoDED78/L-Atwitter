package com.gmail.javacoded78.latwitter.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.javacoded78.latwitter.dto.Views;
import lombok.Data;

@Data
public class AuthenticationResponse {

    @JsonView(Views.User.class)
    private UserResponse user;

    @JsonView(Views.User.class)
    private String token;
}
