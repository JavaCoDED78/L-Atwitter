package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.response.notification.NotificationListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.gmail.javacoded78.constants.FeignConstants.LISTS_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_LISTS;

@FeignClient(name = LISTS_SERVICE, configuration = FeignConfiguration.class)
public interface ListsClient {

    @GetMapping(API_V1_LISTS + "/{listId}")
    NotificationListResponse getNotificationList(@PathVariable("listId") Long listId);
}