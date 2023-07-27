package com.gmail.javacoded78.user;

import com.gmail.javacoded78.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("user-service")
public interface AuthenticationClient {


    @GetMapping("/api/v1/auth/user/id")
    Long getAuthenticatedUserId();

    @GetMapping("/api/v1/auth/user")
    User getAuthenticatedUser();
}
