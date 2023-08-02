package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.request.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.gmail.javacoded78.constants.FeignConstants.NOTIFICATION_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_NOTIFICATION;

@FeignClient(value = NOTIFICATION_SERVICE, configuration = FeignConfiguration.class)
public interface NotificationClient {

    @PostMapping(API_V1_NOTIFICATION + "/user")
    void sendUserNotification(@RequestBody NotificationRequest request);
}
