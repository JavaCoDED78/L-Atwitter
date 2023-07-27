package com.gmail.javacoded78.service;

import com.gmail.javacoded78.models.User;
import com.gmail.javacoded78.repository.projection.AuthUserProjection;

public interface UserService {

    User getUserById(Long userId);

    AuthUserProjection getAuthUserByEmail(String email);
}
