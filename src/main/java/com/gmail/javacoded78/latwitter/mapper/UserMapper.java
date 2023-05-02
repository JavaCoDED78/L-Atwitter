package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserResponse convertToResponseDto(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponse getUserById(Long userId) {
        return convertToResponseDto(userService.getUserById(userId));
    }
}
