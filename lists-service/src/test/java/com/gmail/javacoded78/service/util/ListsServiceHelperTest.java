package com.gmail.javacoded78.service.util;

import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.dto.response.lists.ListMemberResponse;
import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.NotificationClient;
import com.gmail.javacoded78.repository.ListsRepository;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_LIST_NAME_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.LIST_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_BLOCKED;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class ListsServiceHelperTest {

    @Mock
    private final ListsRepository listsRepository;

    @Mock
    private final ListsFollowersRepository listsFollowersRepository;

    @Mock
    private final ListsMembersRepository listsMembersRepository;

    @Mock
    private final PinnedListsRepository pinnedListsRepository;

    @Mock
    private final NotificationClient notificationClient;

    @Mock
    private final UserClient userClient;

    @InjectMocks
    private final ListsServiceHelper listsServiceHelper;

    @BeforeEach
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    void getListMemberResponses() {
        List<Long> membersIds = List.of(1L, 2L, 3L);
        List<ListMemberResponse> mockListMemberResponseList = ListsServiceTestHelper.createMockListMemberResponseList();
        when(listsMembersRepository.getMembersIds(TestConstants.LIST_ID)).thenReturn(membersIds);
        when(userClient.getListParticipantsByIds(new IdsRequest(membersIds))).thenReturn(mockListMemberResponseList);
        assertEquals(mockListMemberResponseList, listsServiceHelper.getListMemberResponses(TestConstants.LIST_ID));
        verify(listsMembersRepository, times(1)).getMembersIds(TestConstants.LIST_ID);
        verify(userClient, times(1)).getListParticipantsByIds(new IdsRequest(membersIds));
    }

    @Test
    void isListIncludeUser() {
        when(listsRepository.isListIncludeUser(TestConstants.LIST_ID, USER_ID, 1L)).thenReturn(true);
        assertTrue(listsServiceHelper.isListIncludeUser(TestConstants.LIST_ID, 1L));
        verify(listsRepository, times(1)).isListIncludeUser(TestConstants.LIST_ID, USER_ID, 1L);
    }

    @Test
    void checkUserIsBlocked() {
        when(userClient.isUserBlocked(1L, 1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsServiceHelper.checkUserIsBlocked(1L, 1L));
        assertEquals(String.format(USER_ID_BLOCKED, 1L), exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void checkUserIsNotBlocked() {
        when(userClient.isUserBlocked(1L, 1L)).thenReturn(false);
        listsServiceHelper.checkUserIsBlocked(1L, 1L);
        verify(userClient, times(1)).isUserBlocked(1L, 1L);
    }

    @Test
    void checkIsPrivateUserProfile() {
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsServiceHelper.checkIsPrivateUserProfile(1L));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void checkIsNotPrivateUserProfile() {
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
        listsServiceHelper.checkIsPrivateUserProfile(1L);
        verify(userClient, times(1)).isUserHavePrivateProfile(1L);
    }

    @Test
    void checkIsListPrivate() {
        when(listsRepository.isListPrivate(TestConstants.LIST_ID, USER_ID)).thenReturn(true);
        when(listsFollowersRepository.isListFollowed(USER_ID, TestConstants.LIST_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsServiceHelper.checkIsListPrivate(TestConstants.LIST_ID));
        assertEquals(LIST_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void checkIsListNotPrivate() {
        when(listsRepository.isListPrivate(TestConstants.LIST_ID, USER_ID)).thenReturn(false);
        listsServiceHelper.checkIsListPrivate(TestConstants.LIST_ID);
        verify(listsRepository, times(1)).isListPrivate(TestConstants.LIST_ID, USER_ID);
    }

    @Test
    void checkIsListNotExist() {
        when(listsRepository.isListExist(TestConstants.LIST_ID, 1L)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsServiceHelper.checkIsListExist(TestConstants.LIST_ID, 1L));
        assertEquals(LIST_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void checkIsListExist() {
        when(listsRepository.isListExist(TestConstants.LIST_ID, 1L)).thenReturn(true);
        listsServiceHelper.checkIsListExist(TestConstants.LIST_ID, 1L);
        verify(listsRepository, times(1)).isListExist(TestConstants.LIST_ID, 1L);
    }

    @Test
    void sendNotification() {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationType(NotificationType.LISTS)
                .notificationCondition(true)
                .notifiedUserId(1L)
                .userId(2L)
                .listId(4L)
                .build();
        listsServiceHelper.sendNotification(1L, 2L, 4L);
        verify(notificationClient, times(1)).sendNotification(notificationRequest);
    }

    @Test
    void validateListNameLength_shouldEmptyListName() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsServiceHelper.validateListNameLength(""));
        assertEquals(INCORRECT_LIST_NAME_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void validateListNameLength_shouldLargeListName() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsServiceHelper.validateListNameLength("**************************"));
        assertEquals(INCORRECT_LIST_NAME_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void isMyProfileFollowList() {
        when(listsFollowersRepository.isListFollowed(USER_ID, TestConstants.LIST_ID)).thenReturn(true);
        listsServiceHelper.isMyProfileFollowList(TestConstants.LIST_ID);
        verify(listsFollowersRepository, times(1)).isListFollowed(USER_ID, TestConstants.LIST_ID);
    }

    @Test
    void getListOwnerById() {
        when(userClient.getListOwnerById(1L)).thenReturn(new CommonUserResponse());
        listsServiceHelper.getListOwnerById(1L);
        verify(userClient, times(1)).getListOwnerById(1L);
    }

    @Test
    void isListPinned() {
        when(pinnedListsRepository.isListPinned(1L, USER_ID)).thenReturn(true);
        listsServiceHelper.isListPinned(1L);
        verify(pinnedListsRepository, times(1)).isListPinned(1L, USER_ID);
    }
}