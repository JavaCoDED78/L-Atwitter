package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.response.AuthUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final BasicMapper mapper;
    private final UserService userService;

    public AuthUserResponse getAuthUserByEmail(String email) {
        return mapper.convertToResponse(userService.getAuthUserByEmail(email), AuthUserResponse.class);
    }
}
