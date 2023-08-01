package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.notification.NotificationUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.gmail.javacoded78.controller.PathConstants.API_V1_USER;

@FeignClient(name = "user-service", contextId = "UserClient", configuration = FeignConfiguration.class)
public interface UserClient {

    @GetMapping(API_V1_USER + "/notification/{userId}")
    void increaseNotificationsCount(@PathVariable("userId") Long userId);

    @GetMapping(API_V1_USER + "/notification/user/{userId}")
    NotificationUserResponse getNotificationUser(@PathVariable("userId") Long userId);

    @GetMapping(API_V1_USER + "/subscribers/{userId}")
    List<Long> getSubscribersByUserId(@PathVariable("userId") Long userId);
}
