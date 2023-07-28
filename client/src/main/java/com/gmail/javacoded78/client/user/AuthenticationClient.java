package com.gmail.javacoded78.client.user;

import com.gmail.javacoded78.common.dto.UserPrincipalResponse;
import com.gmail.javacoded78.common.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", contextId = "AuthenticationClient")
public interface AuthenticationClient {

    @GetMapping("/api/v1/auth/user/{email}")
    UserPrincipalResponse getUserPrincipalByEmail(@PathVariable("email") String email);

    @GetMapping("/api/v1/auth/user/id")
    Long getAuthenticatedUserId();

    @GetMapping("/api/v1/auth/user")
    User getAuthenticatedUser();
}
