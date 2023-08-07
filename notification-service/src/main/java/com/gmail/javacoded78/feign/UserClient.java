package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.gmail.javacoded78.constants.FeignConstants.USER_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_USER;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_RESET;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_USER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBERS;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBERS_IDS;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBERS_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.USER_ID;

@CircuitBreaker(name = USER_SERVICE)
@FeignClient(name = USER_SERVICE, path = API_V1_USER, contextId = "UserClient", configuration = FeignConfiguration.class)
public interface UserClient {

    @GetMapping(USER_ID)
    UserResponse getUserById(@PathVariable("userId") Long userId);

    @GetMapping(NOTIFICATION_USER_ID)
    void increaseNotificationsCount(@PathVariable("userId") Long userId);

    @GetMapping(NOTIFICATION_USER_USER_ID)
    NotificationUserResponse getNotificationUser(@PathVariable("userId") Long userId);

    @GetMapping(NOTIFICATION_RESET)
    void resetNotificationCount();

    @GetMapping(SUBSCRIBERS_USER_ID)
    List<Long> getSubscribersByUserId(@PathVariable("userId") Long userId);

    @GetMapping(SUBSCRIBERS)
    List<NotificationUserResponse> getUsersWhichUserSubscribed();

    @GetMapping(SUBSCRIBERS_IDS)
    List<Long> getUserIdsWhichUserSubscribed();
}
