package com.gmail.javacoded78.service.util;

import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.dto.response.lists.ListMemberResponse;
import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.NotificationClient;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.repository.ListsFollowersRepository;
import com.gmail.javacoded78.repository.ListsMembersRepository;
import com.gmail.javacoded78.repository.ListsRepository;
import com.gmail.javacoded78.repository.PinnedListsRepository;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_LIST_NAME_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.LIST_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.LIST_OWNER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_BLOCKED;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListsServiceHelper {

    private final ListsRepository listsRepository;
    private final ListsFollowersRepository listsFollowersRepository;
    private final ListsMembersRepository listsMembersRepository;
    private final PinnedListsRepository pinnedListsRepository;
    private final NotificationClient notificationClient;
    private final UserClient userClient;

    public List<ListMemberResponse> getListMemberResponses(Long listId) {
        List<Long> membersIds = listsMembersRepository.getMembersIds(listId);
        return userClient.getListParticipantsByIds(new IdsRequest(membersIds));
    }

    public boolean isListIncludeUser(Long listId, Long memberId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return listsRepository.isListIncludeUser(listId, authUserId, memberId);
    }

    public void checkUserIsBlocked(Long userId, Long supposedBlockedUserId) {
        if (userClient.isUserBlocked(userId, supposedBlockedUserId)) {
            throw new ApiRequestException(String.format(USER_ID_BLOCKED, supposedBlockedUserId), HttpStatus.BAD_REQUEST);
        }
    }

    public void checkIsPrivateUserProfile(Long userId) {
        if (userClient.isUserHavePrivateProfile(userId)) {
            throw new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public void checkIsListPrivate(Long listId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        if (listsRepository.isListPrivate(listId, authUserId) && !isMyProfileFollowList(listId)) {
            throw new ApiRequestException(LIST_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public void checkIsListExist(Long listId, Long listOwnerId) {
        if (!listsRepository.isListExist(listId, listOwnerId)) {
            throw new ApiRequestException(LIST_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public void sendNotification(Long notifiedUserId, Long userId, Long listId) {
        NotificationRequest request = NotificationRequest.builder()
                .notificationType(NotificationType.LISTS)
                .notificationCondition(true)
                .notifiedUserId(notifiedUserId)
                .userId(userId)
                .listId(listId)
                .build();
        notificationClient.sendNotification(request);
    }

    public void validateListNameLength(String listName) {
        if (listName.length() == 0 || listName.length() > 25) {
            throw new ApiRequestException(INCORRECT_LIST_NAME_LENGTH, HttpStatus.BAD_REQUEST);
        }
    }

    public void validateListOwner(Long listOwnerId, Long authUserId) {
        if (!listOwnerId.equals(authUserId)) {
            throw new ApiRequestException(LIST_OWNER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public boolean isMyProfileFollowList(Long listId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return listsRepository.isListFollowed(listId, authUserId);
    }

    public CommonUserResponse getListOwnerById(Long userId) {
        return userClient.getListOwnerById(userId);
    }

    public boolean isListPinned(Long listId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return listsRepository.isListPinned(listId, authUserId);
    }
}
