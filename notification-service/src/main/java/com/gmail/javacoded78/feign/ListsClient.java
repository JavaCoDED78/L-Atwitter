package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.notification.NotificationListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.gmail.javacoded78.controller.PathConstants.API_V1_LISTS;

@FeignClient(name = "lists-service", configuration = FeignConfiguration.class)
public interface ListsClient {

    @GetMapping(API_V1_LISTS + "/{listId}")
    NotificationListResponse getNotificationList(@PathVariable("listId") Long listId);
}