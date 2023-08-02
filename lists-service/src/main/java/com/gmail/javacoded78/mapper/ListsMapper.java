package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.request.ListsRequest;
import com.gmail.javacoded78.dto.request.UserToListsRequest;
import com.gmail.javacoded78.dto.response.BaseListResponse;
import com.gmail.javacoded78.dto.response.lists.ListMemberResponse;
import com.gmail.javacoded78.dto.response.ListResponse;
import com.gmail.javacoded78.dto.response.ListUserResponse;
import com.gmail.javacoded78.dto.response.PinnedListResponse;
import com.gmail.javacoded78.dto.response.SimpleListResponse;
import com.gmail.javacoded78.model.Lists;
import com.gmail.javacoded78.repository.projection.BaseListProjection;
import com.gmail.javacoded78.repository.projection.ListProjection;
import com.gmail.javacoded78.repository.projection.ListUserProjection;
import com.gmail.javacoded78.repository.projection.PinnedListProjection;
import com.gmail.javacoded78.service.ListsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListsMapper {

    private final BasicMapper basicMapper;
    private final ListsService listsService;

    public List<ListResponse> getAllTweetLists() {
        List<ListProjection> lists = listsService.getAllTweetLists();
        return basicMapper.convertToResponseList(lists, ListResponse.class);
    }

    public List<ListUserResponse> getUserTweetLists() {
        List<ListUserProjection> lists = listsService.getUserTweetLists();
        return basicMapper.convertToResponseList(lists, ListUserResponse.class);
    }

    public List<ListResponse> getUserTweetListsById(Long userId) {
        List<ListProjection> lists = listsService.getUserTweetListsById(userId);
        return basicMapper.convertToResponseList(lists, ListResponse.class);
    }

    public List<ListResponse> getTweetListsWhichUserIn() {
        List<ListProjection> lists = listsService.getTweetListsWhichUserIn();
        return basicMapper.convertToResponseList(lists, ListResponse.class);
    }

    public List<PinnedListResponse> getUserPinnedLists() {
        List<PinnedListProjection> userPinnedLists = listsService.getUserPinnedLists();
        return basicMapper.convertToResponseList(userPinnedLists, PinnedListResponse.class);
    }

    public BaseListResponse getListById(Long listId) {
        BaseListProjection list = listsService.getListById(listId);
        return basicMapper.convertToResponse(list, BaseListResponse.class);
    }

    public ListUserResponse createTweetList(ListsRequest listsRequest) {
        ListUserProjection list = listsService.createTweetList(basicMapper.convertToResponse(listsRequest, Lists.class));
        return basicMapper.convertToResponse(list, ListUserResponse.class);
    }

    public BaseListResponse editTweetList(ListsRequest listsRequest) {
        BaseListProjection list = listsService.editTweetList(basicMapper.convertToResponse(listsRequest, Lists.class));
        return basicMapper.convertToResponse(list, BaseListResponse.class);
    }

    public String deleteList(Long listId) {
        return listsService.deleteList(listId);
    }

    public ListUserResponse followList(Long listId) {
        ListUserProjection list = listsService.followList(listId);
        return basicMapper.convertToResponse(list, ListUserResponse.class);
    }

    public PinnedListResponse pinList(Long listId) {
        PinnedListProjection list = listsService.pinList(listId);
        return basicMapper.convertToResponse(list, PinnedListResponse.class);
    }

    public List<SimpleListResponse> getListsToAddUser(Long userId) {
        List<Map<String, Object>> userLists = listsService.getListsToAddUser(userId);
        return userLists.stream()
                .map(list -> {
                    SimpleListResponse simpleListResponse = basicMapper.convertToResponse(list.get("list"), SimpleListResponse.class);
                    simpleListResponse.setMemberInList((Boolean) list.get("isMemberInList"));
                    return simpleListResponse;
                })
                .collect(Collectors.toList());
    }

    public String addUserToLists(UserToListsRequest userToListsRequest) {
        return listsService.addUserToLists(userToListsRequest);
    }

    public Boolean addUserToList(Long userId, Long listId) {
        return listsService.addUserToList(userId, listId);
    }

    public HeaderResponse<TweetResponse> getTweetsByListId(Long listId, Pageable pageable) {
        return listsService.getTweetsByListId(listId, pageable);
    }

    public BaseListResponse getListDetails(Long listId) {
        BaseListProjection list = listsService.getListDetails(listId);
        return basicMapper.convertToResponse(list, BaseListResponse.class);
    }

    public List<ListMemberResponse> getListFollowers(Long listId, Long listOwnerId) {
        return listsService.getListFollowers(listId, listOwnerId);
    }

    public List<ListMemberResponse> getListMembers(Long listId, Long listOwnerId) {
        return listsService.getListMembers(listId, listOwnerId);
    }

    public List<ListMemberResponse> searchListMembersByUsername(Long listId, String username) {
        return listsService.searchListMembersByUsername(listId, username);
    }
}
