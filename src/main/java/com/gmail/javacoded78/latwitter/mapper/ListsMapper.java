package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.request.ListsRequest;
import com.gmail.javacoded78.latwitter.dto.response.ListsResponse;
import com.gmail.javacoded78.latwitter.model.Lists;
import com.gmail.javacoded78.latwitter.service.ListsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListsMapper {

    private final ModelMapper modelMapper;
    private final ListsService listsService;

    private Lists convertToListsEntity(ListsRequest listsRequest) {
        return modelMapper.map(listsRequest, Lists.class);
    }

    private ListsResponse convertToListsResponse(Lists lists) {
        return modelMapper.map(lists, ListsResponse.class);
    }

    private List<ListsResponse> convertListToResponse(List<Lists> tweets) {
        return tweets.stream()
                .map(this::convertToListsResponse)
                .collect(Collectors.toList());
    }

    public List<ListsResponse> getAllTweetLists() {
        return convertListToResponse(listsService.getAllTweetLists());
    }

    public List<ListsResponse> getUserTweetLists() {
        return convertListToResponse(listsService.getUserTweetLists());
    }

    public ListsResponse getListById(Long listId) {
        return convertToListsResponse(listsService.getListById(listId));
    }

    public ListsResponse createTweetList(ListsRequest listsRequest) {
        return convertToListsResponse(listsService.createTweetList(convertToListsEntity(listsRequest)));
    }

    public ListsResponse followList(Long listId) {
        return convertToListsResponse(listsService.followList(listId));
    }
}
