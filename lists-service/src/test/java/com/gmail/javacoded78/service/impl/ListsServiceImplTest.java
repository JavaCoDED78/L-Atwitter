package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.request.ListsRequest;
import com.gmail.javacoded78.dto.request.UserToListsRequest;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.NotificationClient;
import com.gmail.javacoded78.feign.TweetClient;
import com.gmail.javacoded78.model.Lists;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.ListsRepository;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projection.BaseListProjection;
import com.gmail.javacoded78.repository.projection.ListProjection;
import com.gmail.javacoded78.repository.projection.ListUserProjection;
import com.gmail.javacoded78.service.ListsService;
import com.gmail.javacoded78.service.UserService;
import com.gmail.javacoded78.service.util.ListsServiceHelper;
import com.gmail.javacoded78.service.util.ListsServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_LIST_NAME_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.LIST_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.LIST_OWNER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_BLOCKED;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.util.TestConstants.LIST_USER_ID;
import static com.gmail.javacoded78.util.TestConstants.USERNAME;
import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
public class ListsServiceImplTest {

    @Autowired
    private ListsService listsService;

    @Autowired
    private ListsServiceHelper listsServiceHelper;

    @MockBean
    private ListsRepository listsRepository;

    @MockBean
    private UserRepository userRepository;

//    @MockBean
//    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;

    @MockBean
    private TweetClient tweetClient;

    @MockBean
    private NotificationClient notificationClient;

    @BeforeEach
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @org.junit.Test
    public void getAllTweetLists() {
        List<ListProjection> mockListProjectionList = ListsServiceTestHelper.createMockListProjectionList();
        when(listsRepository.getAllTweetLists(USER_ID)).thenReturn(mockListProjectionList);
        assertEquals(2, listsService.getAllTweetLists().size());
        verify(listsRepository, times(1)).getAllTweetLists(USER_ID);
    }

    @org.junit.Test
    public void getUserTweetLists() {
        when(listsRepository.getUserTweetLists(USER_ID))
                .thenReturn(ListsServiceTestHelper.createMockListUserProjectionList());
        assertEquals(2, listsService.getUserTweetLists().size());
        verify(listsRepository, times(1)).getUserTweetLists(USER_ID);
    }

    @org.junit.Test
    public void getUserPinnedLists() {
        when(listsRepository.getUserPinnedLists(USER_ID))
                .thenReturn(ListsServiceTestHelper.createMockPinnedListProjectionList());
        assertEquals(2, listsService.getUserPinnedLists().size());
        verify(listsRepository, times(1)).getUserPinnedLists(USER_ID);
    }

    @org.junit.Test
    public void getListById() {
        BaseListProjection baseListProjection = ListsServiceTestHelper.createMockBaseListProjection(LIST_USER_ID);
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, BaseListProjection.class))
                .thenReturn(Optional.of(baseListProjection));
        assertEquals(baseListProjection, listsService.getListById(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, BaseListProjection.class);
        verify(userRepository, never()).isUserHavePrivateProfile(LIST_USER_ID, USER_ID);
        verify(userRepository, times(1)).isUserBlocked(LIST_USER_ID, USER_ID);
    }

    @org.junit.Test
    public void getListById_shouldCheckIsPrivateUserProfile() {
        BaseListProjection baseListProjection = ListsServiceTestHelper.createMockBaseListProjection(3L);
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, BaseListProjection.class))
                .thenReturn(Optional.of(baseListProjection));
        when(userRepository.isUserHavePrivateProfile(3L, USER_ID)).thenReturn(true);
        assertEquals(baseListProjection, listsService.getListById(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, BaseListProjection.class);
        verify(userRepository, times(1)).isUserHavePrivateProfile(3L, USER_ID);
        verify(userRepository, times(1)).isUserBlocked(3L, USER_ID);
    }

    @org.junit.Test
    public void getListById_shouldCheckUserPrivateProfileAndReturnUserNotFound() {
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, BaseListProjection.class))
                .thenReturn(Optional.of(ListsServiceTestHelper.createMockBaseListProjection(3L)));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsService.getListById(TestConstants.LIST_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @org.junit.Test
    public void getListById_shouldCheckUserIsBlockedAndReturnBlockedUser() {
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, BaseListProjection.class))
                .thenReturn(Optional.of(ListsServiceTestHelper.createMockBaseListProjection(3L)));
        when(userRepository.isUserHavePrivateProfile(3L, USER_ID)).thenReturn(true);
        when(userRepository.isUserBlocked(3L, USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsService.getListById(TestConstants.LIST_ID));
        assertEquals(String.format(USER_ID_BLOCKED, 2L), exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @org.junit.Test
    public void getListById_shouldReturnListNotFound() {
        when(listsRepository.getListById(LIST_USER_ID, USER_ID, BaseListProjection.class)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> listsService.getListById(LIST_USER_ID));
        assertEquals(LIST_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @org.junit.Test
    public void createTweetList() {
        User user = ListsServiceTestHelper.mockUser(USER_ID);
        ListsRequest listsRequest = new ListsRequest();
        listsRequest.setListName(TestConstants.LIST_NAME);
        listsRequest.setIsPrivate(false);
        listsRequest.setDescription(TestConstants.LIST_DESCRIPTION);
        listsRequest.setAltWallpaper(TestConstants.LIST_ALT_WALLPAPER);
        listsRequest.setWallpaper("");
        ListUserProjection listUser = ListsServiceTestHelper.createMockListUserProjectionList().get(0);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(listsRepository.getListById(null, ListUserProjection.class)).thenReturn(listUser);
        listsService.createTweetList(listsRequest);
        verify(listsRepository, times(1)).getListById(null, ListUserProjection.class);
    }

    @org.junit.Test
    public void createTweetList_shouldEmptyListNameAndReturnException() {
        ListsRequest listsRequest = new ListsRequest();
        listsRequest.setListName("");
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsService.createTweetList(listsRequest));
        assertEquals(INCORRECT_LIST_NAME_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @org.junit.Test
    public void createTweetList_shouldLargeListNameAndReturnException() {
        ListsRequest lists = new ListsRequest();
        lists.setId(1L);
        lists.setListName("**************************");
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsService.createTweetList(lists));
        assertEquals(INCORRECT_LIST_NAME_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @org.junit.Test
    public void getUserTweetListsById() {
        when(listsRepository.getUserTweetListsById(USER_ID))
                .thenReturn(ListsServiceTestHelper.createMockListProjectionList());
        assertEquals(2, listsService.getUserTweetListsById(USER_ID).size());
        verify(listsRepository, times(1)).getUserTweetListsById(USER_ID);
    }

    @org.junit.Test
    public void getUserTweetListsById_shouldUserBlockedAndReturnEmptyList() {
        when(userRepository.isUserBlocked(USER_ID, 3L)).thenReturn(true);
        when(userRepository.isUserHavePrivateProfile(3L, USER_ID)).thenReturn(false);
        assertEquals(0, listsService.getUserTweetListsById(3L).size());
        verify(listsRepository, never()).getUserTweetListsById(3L);
    }

    @org.junit.Test
    public void getUserTweetListsById_shouldUserPrivateAndReturnEmptyList() {
        when(userRepository.isUserBlocked(USER_ID, 3L)).thenReturn(false);
        when(userRepository.isUserHavePrivateProfile(3L, USER_ID)).thenReturn(true);
        assertEquals(0, listsService.getUserTweetListsById(3L).size());
        verify(userRepository, times(1)).isUserBlocked(USER_ID, 3L);
        verify(userRepository, times(1)).isUserHavePrivateProfile(3L, USER_ID);
    }

    @org.junit.Test
    public void getUserTweetListsById_shouldReturnUserTweetLists() {
        when(listsRepository.getUserTweetListsById(3L))
                .thenReturn(ListsServiceTestHelper.createMockListProjectionList());
        when(userRepository.isUserBlocked(USER_ID, 3L)).thenReturn(false);
        when(userRepository.isUserHavePrivateProfile(3L, USER_ID)).thenReturn(true);
        assertEquals(2, listsService.getUserTweetListsById(3L).size());
        verify(listsRepository, times(1)).getUserTweetListsById(3L);
        verify(userRepository, times(1)).isUserBlocked(USER_ID, 3L);
        verify(userRepository, times(1)).isUserHavePrivateProfile(3L, USER_ID);
    }

    @org.junit.Test
    public void getTweetListsWhichUserIn() {
        when(listsRepository.getTweetListsWhichUserIn(USER_ID))
                .thenReturn(ListsServiceTestHelper.createMockListProjectionList());
        assertEquals(2, listsService.getTweetListsWhichUserIn().size());
        verify(listsRepository, times(1)).getTweetListsWhichUserIn(USER_ID);
    }

    @org.junit.Test
    public void editTweetList() {
        ListsRequest lists = new ListsRequest();
        lists.setId(1L);
        lists.setListName(TestConstants.LIST_NAME);
        lists.setDescription(TestConstants.LIST_DESCRIPTION);
        lists.setWallpaper("");
        lists.setIsPrivate(false);
        BaseListProjection baseListProjection = ListsServiceTestHelper.createMockBaseListProjection(USER_ID);
        when(listsRepository.getListByIdAndUserId(TestConstants.LIST_ID, USER_ID)).thenReturn(Optional.of(ListsServiceTestHelper.createMockLists()));
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, BaseListProjection.class)).thenReturn(Optional.of(baseListProjection));
        BaseListProjection baseList = listsService.editTweetList(lists);
        assertEquals(baseListProjection, baseList);
        assertEquals(TestConstants.LIST_NAME, baseList.getListName());
        assertEquals(TestConstants.LIST_DESCRIPTION, baseList.getDescription());
        assertEquals("", baseList.getWallpaper());
        assertFalse(baseList.getIsPrivate());
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, BaseListProjection.class);
    }

    @org.junit.Test
    public void editTweetList_shouldReturnNotFound() {
        when(listsRepository.getListByIdAndUserId(TestConstants.LIST_ID, USER_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> listsService.editTweetList(new ListsRequest()));
        assertEquals(LIST_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @org.junit.Test
    public void editTweetList_shouldEmptyListNameAndReturnException() {
        ListsRequest lists = new ListsRequest();
        lists.setId(1L);
        lists.setListName("");
        when(listsRepository.getListByIdAndUserId(TestConstants.LIST_ID, USER_ID)).thenReturn(Optional.of(ListsServiceTestHelper.createMockLists()));
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> listsService.editTweetList(lists));
        assertEquals(INCORRECT_LIST_NAME_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @org.junit.Test
    public void editTweetList_shouldLargeListNameAndReturnException() {
        ListsRequest lists = new ListsRequest();
        lists.setId(1L);
        lists.setListName("**************************");
        when(listsRepository.getListByIdAndUserId(TestConstants.LIST_ID, USER_ID)).thenReturn(Optional.of(ListsServiceTestHelper.createMockLists()));
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> listsService.editTweetList(lists));
        assertEquals(INCORRECT_LIST_NAME_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @org.junit.Test
    public void deleteList() {
        Lists list = ListsServiceTestHelper.createMockLists();
        when(listsRepository.getListByIdAndUserId(TestConstants.LIST_ID, USER_ID)).thenReturn(Optional.of(list));
        assertEquals(String.format("List id:%s deleted.", TestConstants.LIST_ID), listsService.deleteList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListByIdAndUserId(TestConstants.LIST_ID, USER_ID);
        verify(listsRepository, times(1)).delete(list);
    }

    @org.junit.Test
    public void deleteList_shouldListNotFound() {
        when(listsRepository.getListByIdAndUserId(TestConstants.LIST_ID, USER_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsService.deleteList(TestConstants.LIST_ID));
        assertEquals(LIST_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @org.junit.Test
    public void followList_followSuccess() {
        ListUserProjection listUser = ListsServiceTestHelper.createMockListUserProjectionList().get(0);
        Lists lists = ListsServiceTestHelper.createMockLists();
        User user = ListsServiceTestHelper.mockUser(USER_ID);
        when(listsRepository.getListByIdAndIsPrivateFalse(TestConstants.LIST_ID)).thenReturn(Optional.of(lists));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(listsRepository.getListById(TestConstants.LIST_ID, ListUserProjection.class)).thenReturn(listUser);
        assertEquals(listUser, listsService.followList(TestConstants.LIST_ID));
        assertEquals(1, lists.getListsFollowers().size());
        verify(listsRepository, times(1)).getListByIdAndIsPrivateFalse(TestConstants.LIST_ID);
        verify(userRepository, times(1)).findById(USER_ID);
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, ListUserProjection.class);
    }

    @org.junit.Test
    public void followList_unfollowSuccess() {
        ListUserProjection listUser = ListsServiceTestHelper.createMockListUserProjectionList().get(0);
        Lists lists = ListsServiceTestHelper.createMockLists();
        User user = ListsServiceTestHelper.mockUser(USER_ID);
        lists.setListsFollowers(new HashSet<>(Set.of(user)));
        when(listsRepository.getListByIdAndIsPrivateFalse(TestConstants.LIST_ID)).thenReturn(Optional.of(lists));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(listsRepository.getListById(TestConstants.LIST_ID, ListUserProjection.class)).thenReturn(listUser);
        assertEquals(listUser, listsService.followList(TestConstants.LIST_ID));
        assertEquals(0, lists.getListsFollowers().size());
        verify(listsRepository, times(1)).getListByIdAndIsPrivateFalse(TestConstants.LIST_ID);
        verify(userRepository, times(1)).findById(USER_ID);
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, ListUserProjection.class);
    }

    @org.junit.Test
    public void followList_shoutReturnListNotFoundException() {
        when(listsRepository.getListByIdAndIsPrivateFalse(TestConstants.LIST_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsService.followList(TestConstants.LIST_ID));
        assertEquals(LIST_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

//    @Test
//    public void pinList_pinSuccess() {
//        testPinList(false);
//    }
//
//    @Test
//    public void pinList_unpinSuccess() {
//        testPinList(true);
//    }

    @org.junit.Test
    public void pinList_shouldReturnListNotFound() {
        User user = ListsServiceTestHelper.mockUser(USER_ID);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(listsRepository.getListWhereUserConsist(TestConstants.LIST_ID, USER_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> listsService.pinList(TestConstants.LIST_ID));
        assertEquals(LIST_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @org.junit.Test
    public void getListsToAddUser() {
        when(listsRepository.getUserOwnerLists(USER_ID))
                .thenReturn(ListsServiceTestHelper.createMockSimpleListProjectionList());
        assertNotNull(listsService.getListsToAddUser(1L));
        verify(listsRepository, times(1)).getUserOwnerLists(USER_ID);
        verify(listsRepository, times(1)).isListIncludeUser(1L, USER_ID, 1L);
        verify(listsRepository, times(1)).isListIncludeUser(2L, USER_ID, 1L);
    }

    @Test
    public void addUserToLists() {
        UserToListsRequest listsRequest = ListsServiceTestHelper.mockUserToListsRequest();
        User authUser = ListsServiceTestHelper.mockUser(USER_ID);
        User user = ListsServiceTestHelper.mockUser(1L);
        Lists mockLists1 = ListsServiceTestHelper.createMockLists();
        Lists mockLists2 = ListsServiceTestHelper.createMockLists();
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(authUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.isUserBlocked(USER_ID, 1L)).thenReturn(false);
        when(userRepository.isUserBlocked(1L, USER_ID)).thenReturn(false);
        when(listsRepository.getListWhereUserConsist(listsRequest.getLists().get(0).getListId(), USER_ID)).thenReturn(Optional.of(mockLists1));
        when(listsRepository.getListWhereUserConsist(listsRequest.getLists().get(1).getListId(), USER_ID)).thenReturn(Optional.of(mockLists2));


//        ListsMembers member = new ListsMembers(1L, listsRequest.getUserId());
//        when(userClient.isUserBlocked(USER_ID, listsRequest.getUserId())).thenReturn(false);
//        when(userClient.isUserBlocked(listsRequest.getUserId(), USER_ID)).thenReturn(false);
//        when(userClient.isUserHavePrivateProfile(listsRequest.getUserId())).thenReturn(false);
//        when(listsRepository.isListExist(1L, USER_ID)).thenReturn(true);
//        when(listsRepository.isListExist(2L, USER_ID)).thenReturn(true);
//        when(listsMembersRepository.getListMember(1L, listsRequest.getUserId())).thenReturn(member);
//        when(listsMembersRepository.getListMember(2L, listsRequest.getUserId())).thenReturn(null);
//        assertEquals("User added to lists success.", listsService.addUserToLists(listsRequest));
//        verify(userClient, times(1)).isUserBlocked(USER_ID, listsRequest.getUserId());
//        verify(userClient, times(1)).isUserBlocked(listsRequest.getUserId(), USER_ID);
//        verify(userClient, times(1)).isUserHavePrivateProfile(listsRequest.getUserId());
//        verify(listsRepository, times(1)).isListExist(1L, USER_ID);
//        verify(listsRepository, times(1)).isListExist(2L, USER_ID);
//        verify(listsMembersRepository, times(1)).getListMember(1L, listsRequest.getUserId());
//        verify(listsMembersRepository, times(1)).getListMember(2L, listsRequest.getUserId());
//        verify(listsMembersRepository, times(1)).delete(member);
//        verify(listsMembersRepository, times(1)).save(new ListsMembers(2L, listsRequest.getUserId()));
//        verify(notificationClient, times(1)).sendNotification(any());
    }

//    @Test
//    public void addUserToLists_shouldCheckUserIsBlockedAndReturnBlockedUser() {
//        UserToListsRequest listsRequest = ListsServiceTestHelper.mockUserToListsRequest();
//        when(userClient.isUserBlocked(USER_ID, listsRequest.getUserId())).thenReturn(true);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.addUserToLists(listsRequest));
//        assertEquals(String.format(USER_ID_BLOCKED, listsRequest.getUserId()), exception.getMessage());
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//    }
//
//    @Test
//    public void addUserToLists_shouldCheckUserIsBlockedMyProfileAndReturnBlockedUser() {
//        UserToListsRequest listsRequest = ListsServiceTestHelper.mockUserToListsRequest();
//        when(userClient.isUserBlocked(USER_ID, listsRequest.getUserId())).thenReturn(false);
//        when(userClient.isUserBlocked(listsRequest.getUserId(), USER_ID)).thenReturn(true);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.addUserToLists(listsRequest));
//        assertEquals(String.format(USER_ID_BLOCKED, USER_ID), exception.getMessage());
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//    }
//
//    @Test
//    public void addUserToLists_shouldCheckUserPrivateProfileAndReturnUserNotFound() {
//        UserToListsRequest listsRequest = ListsServiceTestHelper.mockUserToListsRequest();
//        when(userClient.isUserBlocked(USER_ID, listsRequest.getUserId())).thenReturn(false);
//        when(userClient.isUserBlocked(listsRequest.getUserId(), USER_ID)).thenReturn(false);
//        when(userClient.isUserHavePrivateProfile(listsRequest.getUserId())).thenReturn(true);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.addUserToLists(listsRequest));
//        assertEquals(USER_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void addUserToLists_shouldCheckIsListExistsAndReturnListNotFound() {
//        UserToListsRequest listsRequest = ListsServiceTestHelper.mockUserToListsRequest();
//        when(userClient.isUserBlocked(USER_ID, listsRequest.getUserId())).thenReturn(false);
//        when(userClient.isUserBlocked(listsRequest.getUserId(), USER_ID)).thenReturn(false);
//        when(userClient.isUserHavePrivateProfile(listsRequest.getUserId())).thenReturn(false);
//        when(listsRepository.isListExist(1L, USER_ID)).thenReturn(false);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.addUserToLists(listsRequest));
//        assertEquals(LIST_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void addUserToList_addUser() {
//        when(userClient.isUserBlocked(USER_ID, 1L)).thenReturn(false);
//        when(userClient.isUserBlocked(1L, USER_ID)).thenReturn(false);
//        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
//        when(listsRepository.isListExist(TestConstants.LIST_ID, USER_ID)).thenReturn(true);
//        when(listsMembersRepository.getListMember(TestConstants.LIST_ID, 1L)).thenReturn(null);
//        assertTrue(listsService.addUserToList(1L, TestConstants.LIST_ID));
//        verify(userClient, times(1)).isUserBlocked(USER_ID, 1L);
//        verify(userClient, times(1)).isUserBlocked(1L, USER_ID);
//        verify(userClient, times(1)).isUserHavePrivateProfile(1L);
//        verify(listsRepository, times(1)).isListExist(TestConstants.LIST_ID, USER_ID);
//        verify(listsMembersRepository, times(1)).save(new ListsMembers(TestConstants.LIST_ID, 1L));
//        verify(notificationClient, times(1)).sendNotification(any());
//    }
//
//    @Test
//    public void addUserToList_removeUser() {
//        ListsMembers listsMembers = new ListsMembers(TestConstants.LIST_ID, 1L);
//        when(userClient.isUserBlocked(USER_ID, 1L)).thenReturn(false);
//        when(userClient.isUserBlocked(1L, USER_ID)).thenReturn(false);
//        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
//        when(listsRepository.isListExist(TestConstants.LIST_ID, USER_ID)).thenReturn(true);
//        when(listsMembersRepository.getListMember(TestConstants.LIST_ID, 1L)).thenReturn(listsMembers);
//        assertFalse(listsService.addUserToList(1L, TestConstants.LIST_ID));
//        verify(userClient, times(1)).isUserBlocked(USER_ID, 1L);
//        verify(userClient, times(1)).isUserBlocked(1L, USER_ID);
//        verify(userClient, times(1)).isUserHavePrivateProfile(1L);
//        verify(listsRepository, times(1)).isListExist(TestConstants.LIST_ID, USER_ID);
//        verify(listsMembersRepository, times(1)).delete(listsMembers);
//    }
//
//    @Test
//    public void addUserToList_shouldCheckUserIsBlockedAndReturnBlockedUser() {
//        when(userClient.isUserBlocked(USER_ID, 1L)).thenReturn(true);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.addUserToList(1L, TestConstants.LIST_ID));
//        assertEquals(String.format(USER_ID_BLOCKED, 1L), exception.getMessage());
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//    }
//
//    @Test
//    public void addUserToList_shouldCheckUserIsBlockedMyProfileAndReturnBlockedUser() {
//        when(userClient.isUserBlocked(USER_ID, 1L)).thenReturn(false);
//        when(userClient.isUserBlocked(1L, USER_ID)).thenReturn(true);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.addUserToList(1L, TestConstants.LIST_ID));
//        assertEquals(String.format(USER_ID_BLOCKED, USER_ID), exception.getMessage());
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//    }
//
//    @Test
//    public void addUserToList_shouldCheckUserPrivateProfileAndReturnUserNotFound() {
//        when(userClient.isUserBlocked(USER_ID, 1L)).thenReturn(false);
//        when(userClient.isUserBlocked(1L, USER_ID)).thenReturn(false);
//        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.addUserToList(1L, TestConstants.LIST_ID));
//        assertEquals(USER_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void addUserToList_shouldCheckIsListExistsAndReturnListNotFound() {
//        when(userClient.isUserBlocked(USER_ID, 1L)).thenReturn(false);
//        when(userClient.isUserBlocked(1L, USER_ID)).thenReturn(false);
//        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
//        when(listsRepository.isListExist(TestConstants.LIST_ID, USER_ID)).thenReturn(false);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.addUserToList(1L, TestConstants.LIST_ID));
//        assertEquals(LIST_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void getTweetsByListId() {
//        PageRequest pageable = PageRequest.of(0, 20);
//        List<Long> membersIds = List.of(1L, 2L, 3L);
//        HeaderResponse<TweetResponse> headerResponse = new HeaderResponse<>(
//                List.of(new TweetResponse(), new TweetResponse()), new HttpHeaders());
//        when(listsRepository.isListNotPrivate(TestConstants.LIST_ID, USER_ID)).thenReturn(true);
//        when(listsMembersRepository.getMembersIds(TestConstants.LIST_ID)).thenReturn(membersIds);
//        when(tweetClient.getTweetsByUserIds(new IdsRequest(membersIds), pageable)).thenReturn(headerResponse);
//        assertEquals(headerResponse, listsService.getTweetsByListId(TestConstants.LIST_ID, pageable));
//        verify(listsRepository, times(1)).isListNotPrivate(TestConstants.LIST_ID, USER_ID);
//        verify(listsMembersRepository, times(1)).getMembersIds(TestConstants.LIST_ID);
//        verify(tweetClient, times(1)).getTweetsByUserIds(new IdsRequest(membersIds), pageable);
//    }
//
//    @Test
//    public void getTweetsByListId_shouldListNotFound() {
//        when(listsRepository.isListNotPrivate(TestConstants.LIST_ID, USER_ID)).thenReturn(false);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.getTweetsByListId(TestConstants.LIST_ID, PageRequest.of(0, 20)));
//        assertEquals(LIST_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void getListDetails() {
//        BaseListProjection baseListProjection = ListsServiceTestHelper.createMockBaseListProjection(USER_ID);
//        when(listsRepository.getListDetails(TestConstants.LIST_ID, USER_ID)).thenReturn(Optional.of(baseListProjection));
//        assertEquals(baseListProjection, listsService.getListDetails(TestConstants.LIST_ID));
//        verify(listsRepository, times(1)).getListDetails(TestConstants.LIST_ID, USER_ID);
//    }
//
//    @Test
//    public void getListDetails_shouldReturnListNotFound() {
//        when(listsRepository.getListDetails(TestConstants.LIST_ID, USER_ID)).thenReturn(Optional.empty());
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.getListDetails(TestConstants.LIST_ID));
//        assertEquals(LIST_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void getListFollowers_shouldGetAuthsUserFollowersList() {
//        List<Long> followersIds = List.of(1L, 2L, 3L);
//        List<ListMemberResponse> ListMemberResponseList = ListsServiceTestHelper.createMockListMemberResponseList();
//        when(listsRepository.isListExist(TestConstants.LIST_ID, USER_ID)).thenReturn(true);
//        when(listsFollowersRepository.getFollowersIds(TestConstants.LIST_ID)).thenReturn(followersIds);
//        when(userClient.getListParticipantsByIds(new IdsRequest(followersIds))).thenReturn(ListMemberResponseList);
//        assertEquals(ListMemberResponseList, listsService.getListFollowers(TestConstants.LIST_ID, USER_ID));
//        verify(listsRepository, times(1)).isListExist(TestConstants.LIST_ID, USER_ID);
//        verify(listsFollowersRepository, times(1)).getFollowersIds(TestConstants.LIST_ID);
//        verify(userClient, times(1)).getListParticipantsByIds(new IdsRequest(followersIds));
//    }
//
//    @Test
//    public void getListFollowers_shouldGetAuthsUserAndReturnListNotFound() {
//        when(listsRepository.isListExist(TestConstants.LIST_ID, USER_ID)).thenReturn(false);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.getListFollowers(TestConstants.LIST_ID, USER_ID));
//        assertEquals(LIST_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void getListFollowers_shouldGetUserFollowersList() {
//        List<Long> followersIds = List.of(1L, 2L, 3L);
//        List<ListMemberResponse> ListMemberResponseList = ListsServiceTestHelper.createMockListMemberResponseList();
//        when(userClient.isUserBlocked(3L, USER_ID)).thenReturn(false);
//        when(listsRepository.isListExist(TestConstants.LIST_ID, 3L)).thenReturn(true);
//        when(listsRepository.isListPrivate(TestConstants.LIST_ID, USER_ID)).thenReturn(false);
//        when(listsFollowersRepository.getFollowersIds(TestConstants.LIST_ID)).thenReturn(followersIds);
//        when(userClient.getListParticipantsByIds(new IdsRequest(followersIds))).thenReturn(ListMemberResponseList);
//        assertEquals(ListMemberResponseList, listsService.getListFollowers(TestConstants.LIST_ID, 3L));
//        verify(userClient, times(1)).isUserBlocked(3L, USER_ID);
//        verify(listsRepository, times(1)).isListExist(TestConstants.LIST_ID, 3L);
//        verify(listsRepository, times(1)).isListPrivate(TestConstants.LIST_ID, USER_ID);
//        verify(listsFollowersRepository, times(1)).getFollowersIds(TestConstants.LIST_ID);
//        verify(userClient, times(1)).getListParticipantsByIds(new IdsRequest(followersIds));
//    }
//
//    @Test
//    public void getListFollowers_shouldGetUserAndReturnUserBlocked() {
//        when(userClient.isUserBlocked(3L, USER_ID)).thenReturn(true);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.getListFollowers(TestConstants.LIST_ID, 3L));
//        assertEquals(String.format(USER_ID_BLOCKED, USER_ID), exception.getMessage());
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//    }
//
//    @Test
//    public void getListFollowers_shouldGetUserAndReturnListNotFound() {
//        when(userClient.isUserBlocked(3L, USER_ID)).thenReturn(false);
//        when(listsRepository.isListExist(TestConstants.LIST_ID, 3L)).thenReturn(false);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.getListFollowers(TestConstants.LIST_ID, 3L));
//        assertEquals(LIST_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void getListFollowers_shouldGetUserAndReturnListPrivate() {
//        when(userClient.isUserBlocked(3L, USER_ID)).thenReturn(false);
//        when(listsRepository.isListExist(TestConstants.LIST_ID, 3L)).thenReturn(true);
//        when(listsRepository.isListPrivate(TestConstants.LIST_ID, USER_ID)).thenReturn(true);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.getListFollowers(TestConstants.LIST_ID, 3L));
//        assertEquals(LIST_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void getListMembers_shouldGetAuthsUserFollowersList() {
//        List<Long> membersIds = List.of(1L, 2L, 3L);
//        when(listsRepository.isListExist(TestConstants.LIST_ID, USER_ID)).thenReturn(true);
//        when(listsMembersRepository.getMembersIds(TestConstants.LIST_ID)).thenReturn(membersIds);
//        when(userClient.getListParticipantsByIds(new IdsRequest(membersIds)))
//                .thenReturn(ListsServiceTestHelper.createMockListMemberResponseList());
//        assertNotNull(listsService.getListMembers(TestConstants.LIST_ID, USER_ID));
//        verify(listsRepository, times(1)).isListExist(TestConstants.LIST_ID, USER_ID);
//        verify(listsMembersRepository, times(1)).getMembersIds(TestConstants.LIST_ID);
//        verify(userClient, times(1)).getListParticipantsByIds(new IdsRequest(membersIds));
//    }
//
//    @Test
//    public void getListMembers_shouldGetAuthsUserAndReturnListNotFound() {
//        when(listsRepository.isListExist(TestConstants.LIST_ID, USER_ID)).thenReturn(false);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.getListMembers(TestConstants.LIST_ID, USER_ID));
//        assertEquals(LIST_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void getListMembers_shouldGetUserFollowersList() {
//        List<Long> followersIds = List.of(1L, 2L, 3L);
//        List<ListMemberResponse> ListMemberResponseList = ListsServiceTestHelper.createMockListMemberResponseList();
//        when(userClient.isUserBlocked(3L, USER_ID)).thenReturn(false);
//        when(listsRepository.isListExist(TestConstants.LIST_ID, 3L)).thenReturn(true);
//        when(listsRepository.isListPrivate(TestConstants.LIST_ID, USER_ID)).thenReturn(false);
//        when(listsMembersRepository.getMembersIds(TestConstants.LIST_ID)).thenReturn(followersIds);
//        when(userClient.getListParticipantsByIds(new IdsRequest(followersIds))).thenReturn(ListMemberResponseList);
//        assertEquals(ListMemberResponseList, listsService.getListMembers(TestConstants.LIST_ID, 3L));
//        verify(userClient, times(1)).isUserBlocked(3L, USER_ID);
//        verify(listsRepository, times(1)).isListExist(TestConstants.LIST_ID, 3L);
//        verify(listsRepository, times(1)).isListPrivate(TestConstants.LIST_ID, USER_ID);
//        verify(listsMembersRepository, times(1)).getMembersIds(TestConstants.LIST_ID);
//        verify(userClient, times(1)).getListParticipantsByIds(new IdsRequest(followersIds));
//    }
//
//    @Test
//    public void getListMembers_shouldGetUserAndReturnUserBlocked() {
//        when(userClient.isUserBlocked(3L, USER_ID)).thenReturn(true);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.getListMembers(TestConstants.LIST_ID, 3L));
//        assertEquals(String.format(USER_ID_BLOCKED, USER_ID), exception.getMessage());
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//    }
//
//    @Test
//    public void getListMembers_shouldGetUserAndReturnListNotFound() {
//        when(userClient.isUserBlocked(3L, USER_ID)).thenReturn(false);
//        when(listsRepository.isListExist(TestConstants.LIST_ID, 3L)).thenReturn(false);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.getListMembers(TestConstants.LIST_ID, 3L));
//        assertEquals(LIST_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void getListMembers_shouldGetUserAndReturnListPrivate() {
//        when(userClient.isUserBlocked(3L, USER_ID)).thenReturn(false);
//        when(listsRepository.isListExist(TestConstants.LIST_ID, 3L)).thenReturn(true);
//        when(listsRepository.isListPrivate(TestConstants.LIST_ID, USER_ID)).thenReturn(true);
//        ApiRequestException exception = assertThrows(ApiRequestException.class,
//                () -> listsService.getListMembers(TestConstants.LIST_ID, 3L));
//        assertEquals(LIST_NOT_FOUND, exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
//    }
//
//    @Test
//    public void searchListMembersByUsername() {
//        List<ListMemberResponse> listMemberResponseList = ListsServiceTestHelper.createMockListMemberResponseList();
//        when(userClient.searchListMembersByUsername(USERNAME)).thenReturn(listMemberResponseList);
//        assertEquals(listMemberResponseList, listsService.searchListMembersByUsername(TestConstants.LIST_ID, USERNAME));
//        verify(userClient, times(1)).searchListMembersByUsername(USERNAME);
//    }
//
//    public void testPinList(boolean isPinned) {
//        PinnedListProjection pinnedList = ListsServiceTestHelper.createMockPinnedListProjectionList().get(0);
//        Lists list = ListsServiceTestHelper.createMockLists();
//        PinnedLists pinnedLists = new PinnedLists(list, USER_ID);
//        when(listsRepository.getListWhereUserConsist(TestConstants.LIST_ID, USER_ID)).thenReturn(Optional.of(list));
//        when(pinnedListsRepository.getPinnedByUserIdAndListId(TestConstants.LIST_ID, USER_ID)).thenReturn(isPinned ? pinnedLists : null);
//        when(listsRepository.getListById(TestConstants.LIST_ID, PinnedListProjection.class)).thenReturn(pinnedList);
//        assertEquals(pinnedList, listsService.pinList(TestConstants.LIST_ID));
//        verify(listsRepository, times(1)).getListWhereUserConsist(TestConstants.LIST_ID, USER_ID);
//        verify(pinnedListsRepository, times(1)).getPinnedByUserIdAndListId(TestConstants.LIST_ID, USER_ID);
//        verify(pinnedListsRepository, isPinned ? times(1) : never()).delete(pinnedLists);
//        verify(pinnedListsRepository, isPinned ? never() : times(1)).save(pinnedLists);
//        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, PinnedListProjection.class);
//    }
}
