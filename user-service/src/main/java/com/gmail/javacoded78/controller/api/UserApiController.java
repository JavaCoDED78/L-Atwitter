package com.gmail.javacoded78.controller.api;


import com.gmail.javacoded78.models.User;
import com.gmail.javacoded78.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserApiController {

    private final UserService userService;

    @GetMapping("/ids")
    public List<Long> getUserFollowersIds() {
        return userService.getUserFollowersIds();
    }

    @PostMapping
    public void saveUser(User user) {
        userService.saveUser(user);
    }
}
