package com.gmail.javacoded78.controller.api;

import com.gmail.javacoded78.dto.notification.NotificationListResponse;
import com.gmail.javacoded78.service.ListsClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.javacoded78.controller.PathConstants.API_V1_LISTS;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_LISTS)
public class ListsApiController {

    private final ListsClientService listsClientService;

    @GetMapping("/{listId}")
    public NotificationListResponse getNotificationList(@PathVariable("listId") Long listId) {
        return listsClientService.getNotificationList(listId);
    }
}