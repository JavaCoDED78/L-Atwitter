package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.request.NotificationRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.gmail.javacoded78.constants.FeignConstants.NOTIFICATION_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_NOTIFICATION;

@CircuitBreaker(name = NOTIFICATION_SERVICE)
@FeignClient(value = NOTIFICATION_SERVICE, path = API_V1_NOTIFICATION, configuration = FeignConfiguration.class)
public interface NotificationClient {

    @PostMapping
    void sendNotification(@RequestBody NotificationRequest request);
}
