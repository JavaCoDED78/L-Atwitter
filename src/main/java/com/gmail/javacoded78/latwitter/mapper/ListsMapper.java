package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.request.ListsRequest;
import com.gmail.javacoded78.latwitter.dto.request.UserToListsRequest;
import com.gmail.javacoded78.latwitter.dto.response.lists.BaseListResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.ListMemberResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.ListResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.ListUserResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.ListsOwnerMemberResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.PinnedListResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.SimpleListResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.latwitter.dto.response.HeaderResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.model.Lists;
import com.gmail.javacoded78.latwitter.model.Notification;
import com.gmail.javacoded78.latwitter.repository.projection.lists.BaseListProjection;
import com.gmail.javacoded78.latwitter.repository.projection.lists.ListMemberProjection;
import com.gmail.javacoded78.latwitter.repository.projection.lists.ListProjection;
import com.gmail.javacoded78.latwitter.repository.projection.lists.ListUserProjection;
import com.gmail.javacoded78.latwitter.repository.projection.lists.ListsOwnerMemberProjection;
import com.gmail.javacoded78.latwitter.repository.projection.lists.PinnedListProjection;
import com.gmail.javacoded78.latwitter.repository.projection.tweet.TweetProjection;
import com.gmail.javacoded78.latwitter.service.ListsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        ListUserProjection list = listsService.createTweetList(basicMapper.convertToEntity(listsRequest, Lists.class));
        return basicMapper.convertToResponse(list, ListUserResponse.class);
    }

    public BaseListResponse editTweetList(ListsRequest listsRequest) {
        BaseListProjection list = listsService.editTweetList(basicMapper.convertToEntity(listsRequest, Lists.class));
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

    public NotificationResponse addUserToList(Long userId, Long listId) {
        Map<String, Object> notificationDetails = listsService.addUserToList(userId, listId);
        Notification notification = (Notification) notificationDetails.get("notification");
        NotificationResponse notificationResponse = basicMapper.convertToResponse(notification, NotificationResponse.class);
        notificationResponse.setAddedToList((Boolean) notificationDetails.get("isAddedToList"));
        return notificationResponse;
    }

    public HeaderResponse<TweetResponse> getTweetsByListId(Long listId, Pageable pageable) {
        Page<TweetProjection> tweets = listsService.getTweetsByListId(listId, pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public BaseListResponse getListDetails(Long listId) {
        BaseListProjection list = listsService.getListDetails(listId);
        return basicMapper.convertToResponse(list, BaseListResponse.class);
    }

    public List<ListMemberResponse> getListFollowers(Long listId, Long listOwnerId) {
        List<ListMemberProjection> followers = listsService.getListFollowers(listId, listOwnerId);
        return basicMapper.convertToResponseList(followers, ListMemberResponse.class);
    }

    public List<?> getListMembers(Long listId, Long listOwnerId) {
        Map<String, Object> listMembers = listsService.getListMembers(listId, listOwnerId);

        if (listMembers.get("userMembers") != null) {
            List<ListMemberProjection> userMembers = (List<ListMemberProjection>) listMembers.get("userMembers");
            return basicMapper.convertToResponseList(userMembers, ListMemberResponse.class);
        } else {
            List<ListsOwnerMemberProjection> userMembers = (List<ListsOwnerMemberProjection>) listMembers.get("authUserMembers");

            if (userMembers.get(0).getMember() == null) {
                return new ArrayList<>();
            } else {
                List<ListsOwnerMemberResponse> members = new ArrayList<>();
                userMembers.forEach(listsMemberProjection -> {
                    ListsOwnerMemberResponse member = basicMapper.convertToResponse(listsMemberProjection.getMember(), ListsOwnerMemberResponse.class);
                    member.setMemberInList(listsMemberProjection.getIsMemberInList());
                    members.add(member);
                });
                return members;
            }
        }
    }

    public List<ListsOwnerMemberResponse> searchListMembersByUsername(Long listId, String username) {
        List<Map<String, Object>> userMembers = listsService.searchListMembersByUsername(listId, username);
        return userMembers.stream()
                .map(userMemberMap -> {
                    ListsOwnerMemberResponse member = basicMapper.convertToResponse(userMemberMap.get("member"), ListsOwnerMemberResponse.class);
                    member.setMemberInList((Boolean) userMemberMap.get("isMemberInList"));
                    return member;
                })
                .collect(Collectors.toList());
    }
}
