package com.gmail.javacoded78.controller.api;

import com.gmail.javacoded78.models.User;
import com.gmail.javacoded78.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationApiController {

    private final AuthenticationService authenticationService;

    @GetMapping("/user/id")
    public Long getAuthenticatedUserId() {
        return authenticationService.getAuthenticatedUserId();
    }

    @GetMapping("/user")
    public User getAuthenticatedUser() {
        return authenticationService.getAuthenticatedUser();
    }
}