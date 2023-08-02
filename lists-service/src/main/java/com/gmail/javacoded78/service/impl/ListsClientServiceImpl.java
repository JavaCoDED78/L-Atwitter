package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.response.notification.NotificationListResponse;
import com.gmail.javacoded78.mapper.BasicMapper;
import com.gmail.javacoded78.repository.ListsRepository;
import com.gmail.javacoded78.repository.projection.NotificationListProjection;
import com.gmail.javacoded78.service.ListsClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListsClientServiceImpl implements ListsClientService {

    private final ListsRepository listsRepository;
    private final BasicMapper basicMapper;

    @Override
    public NotificationListResponse getNotificationList(Long listId) {
        NotificationListProjection list = listsRepository.getListById(listId, NotificationListProjection.class);
        return basicMapper.convertToResponse(list, NotificationListResponse.class);
    }
}
