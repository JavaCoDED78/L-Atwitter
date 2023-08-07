package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.response.notification.NotificationListResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.gmail.javacoded78.constants.FeignConstants.LISTS_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_LISTS;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID;

@CircuitBreaker(name = LISTS_SERVICE)
@FeignClient(name = LISTS_SERVICE, path = API_V1_LISTS, configuration = FeignConfiguration.class)
public interface ListsClient {

    @GetMapping(LIST_ID)
    NotificationListResponse getNotificationList(@PathVariable("listId") Long listId);
}