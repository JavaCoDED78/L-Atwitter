package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.ListsRequest;
import com.gmail.javacoded78.dto.request.UserToListsRequest;
import com.gmail.javacoded78.dto.response.BaseListResponse;
import com.gmail.javacoded78.dto.response.ListResponse;
import com.gmail.javacoded78.dto.response.ListUserResponse;
import com.gmail.javacoded78.dto.response.PinnedListResponse;
import com.gmail.javacoded78.dto.response.lists.ListMemberResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import com.gmail.javacoded78.model.Lists;
import com.gmail.javacoded78.repository.projection.BaseListProjection;
import com.gmail.javacoded78.repository.projection.ListProjection;
import com.gmail.javacoded78.repository.projection.ListUserProjection;
import com.gmail.javacoded78.repository.projection.PinnedListProjection;
import com.gmail.javacoded78.service.ListsService;
import com.gmail.javacoded78.service.util.ListsServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class ListsMapperTest {

    @Mock
    private final BasicMapper basicMapper;

    @Mock
    private final ListsService listsService;
    @InjectMocks
    private final ListsMapper listsMapper;

    @Test
    void getAllTweetLists() {
        List<ListProjection> mockListProjections = ListsServiceTestHelper.createMockListProjectionList();
        when(listsService.getAllTweetLists()).thenReturn(mockListProjections);
        when(basicMapper.convertToResponseList(mockListProjections, ListResponse.class))
                .thenReturn(getMockListsResponses());
        assertThat(listsMapper.getAllTweetLists()).hasSize(2);
        verify(listsService, times(1)).getAllTweetLists();
        verify(basicMapper, times(1)).convertToResponseList(mockListProjections, ListResponse.class);
    }

    @Test
    void getUserTweetLists() {
        List<ListUserProjection> mockListUserProjections = ListsServiceTestHelper.createMockListUserProjectionList();
        when(listsService.getUserTweetLists()).thenReturn(mockListUserProjections);
        when(basicMapper.convertToResponseList(mockListUserProjections, ListUserResponse.class))
                .thenReturn(getMockListUserResponses());
        assertThat(listsMapper.getUserTweetLists()).hasSize(2);
        verify(listsService, times(1)).getUserTweetLists();
        verify(basicMapper, times(1)).convertToResponseList(mockListUserProjections, ListUserResponse.class);
    }

    @Test
    void getUserTweetListsById() {
        List<ListProjection> mockListProjections = ListsServiceTestHelper.createMockListProjectionList();
        when(listsService.getUserTweetListsById(1L)).thenReturn(mockListProjections);
        when(basicMapper.convertToResponseList(mockListProjections, ListResponse.class))
                .thenReturn(getMockListsResponses());
        assertThat(listsMapper.getUserTweetListsById(1L)).hasSize(2);
        verify(listsService, times(1)).getUserTweetListsById(1L);
        verify(basicMapper, times(1)).convertToResponseList(mockListProjections, ListResponse.class);
    }

    @Test
    void getTweetListsWhichUserIn() {
        List<ListProjection> mockListProjections = ListsServiceTestHelper.createMockListProjectionList();
        when(listsService.getTweetListsWhichUserIn()).thenReturn(mockListProjections);
        when(basicMapper.convertToResponseList(mockListProjections, ListResponse.class))
                .thenReturn(getMockListsResponses());
        assertThat(listsMapper.getTweetListsWhichUserIn()).hasSize(2);
        verify(listsService, times(1)).getTweetListsWhichUserIn();
        verify(basicMapper, times(1)).convertToResponseList(mockListProjections, ListResponse.class);
    }

    @Test
    void getUserPinnedLists() {
        List<PinnedListProjection> mockPinnedListProjections = ListsServiceTestHelper.createMockPinnedListProjectionList();
        when(listsService.getUserPinnedLists()).thenReturn(mockPinnedListProjections);
        when(basicMapper.convertToResponseList(mockPinnedListProjections, PinnedListResponse.class))
                .thenReturn(getMockPinnedListResponses());
        assertThat(listsMapper.getUserPinnedLists()).hasSize(2);
        verify(listsService, times(1)).getUserPinnedLists();
        verify(basicMapper, times(1)).convertToResponseList(mockPinnedListProjections, PinnedListResponse.class);
    }

    @Test
    void getListById() {
        BaseListProjection mockBaseListProjection = ListsServiceTestHelper.createMockBaseListProjection(1L);
        BaseListResponse mockBaseListResponse = getMockBaseListResponse();
        when(listsService.getListById(TestConstants.LIST_ID)).thenReturn(mockBaseListProjection);
        when(basicMapper.convertToResponse(mockBaseListProjection, BaseListResponse.class)).thenReturn(mockBaseListResponse);
        assertEquals(mockBaseListResponse, listsMapper.getListById(TestConstants.LIST_ID));
        verify(listsService, times(1)).getListById(TestConstants.LIST_ID);
        verify(basicMapper, times(1)).convertToResponse(mockBaseListProjection, BaseListResponse.class);
    }

    @Test
    void createTweetList() {
        ListsRequest listsRequest = getMockListsRequest();
        ListUserProjection mockListUserProjection = ListsServiceTestHelper.createMockListUserProjectionList().get(0);
        ListUserResponse listUserResponse = getMockListUserResponses().get(0);
        when(listsService.createTweetList(basicMapper.convertToResponse(listsRequest, Lists.class))).thenReturn(mockListUserProjection);
        when(basicMapper.convertToResponse(mockListUserProjection, ListUserResponse.class)).thenReturn(listUserResponse);
        assertEquals(listUserResponse, listsMapper.createTweetList(listsRequest));
        verify(listsService, times(1)).createTweetList(basicMapper.convertToResponse(listsRequest, Lists.class));
        verify(basicMapper, times(1)).convertToResponse(mockListUserProjection, ListUserResponse.class);
    }

    @Test
    void editTweetList() {
        ListsRequest listsRequest = getMockListsRequest();
        BaseListProjection mockBaseListProjection = ListsServiceTestHelper.createMockBaseListProjection(1L);
        BaseListResponse baseListResponse = getMockBaseListResponse();
        when(listsService.editTweetList(basicMapper.convertToResponse(listsRequest, Lists.class))).thenReturn(mockBaseListProjection);
        when(basicMapper.convertToResponse(mockBaseListProjection, BaseListResponse.class)).thenReturn(baseListResponse);
        assertEquals(baseListResponse, listsMapper.editTweetList(listsRequest));
        verify(listsService, times(1)).editTweetList(basicMapper.convertToResponse(listsRequest, Lists.class));
        verify(basicMapper, times(1)).convertToResponse(mockBaseListProjection, BaseListResponse.class);
    }

    @Test
    void deleteList() {
        String mockMessageResponse = "List id:1 deleted.";
        when(listsService.deleteList(TestConstants.LIST_ID)).thenReturn(mockMessageResponse);
        assertEquals(mockMessageResponse, listsMapper.deleteList(TestConstants.LIST_ID));
        verify(listsService, times(1)).deleteList(TestConstants.LIST_ID);
    }

    @Test
    void followList() {
        ListUserProjection mockListUserProjection = ListsServiceTestHelper.createMockListUserProjectionList().get(0);
        ListUserResponse listUserResponse = getMockListUserResponses().get(0);
        when(listsService.followList(TestConstants.LIST_ID)).thenReturn(mockListUserProjection);
        when(basicMapper.convertToResponse(mockListUserProjection, ListUserResponse.class)).thenReturn(listUserResponse);
        assertEquals(listUserResponse, listsMapper.followList(TestConstants.LIST_ID));
        verify(listsService, times(1)).followList(TestConstants.LIST_ID);
        verify(basicMapper, times(1)).convertToResponse(mockListUserProjection, ListUserResponse.class);
    }

    @Test
    void pinList() {
        PinnedListProjection pinnedListProjection = ListsServiceTestHelper.createMockPinnedListProjectionList().get(0);
        PinnedListResponse pinnedListResponse = getMockPinnedListResponses().get(0);
        when(listsService.pinList(TestConstants.LIST_ID)).thenReturn(pinnedListProjection);
        when(basicMapper.convertToResponse(pinnedListProjection, PinnedListResponse.class)).thenReturn(pinnedListResponse);
        assertEquals(pinnedListResponse, listsMapper.pinList(TestConstants.LIST_ID));
        verify(listsService, times(1)).pinList(TestConstants.LIST_ID);
        verify(basicMapper, times(1)).convertToResponse(pinnedListProjection, PinnedListResponse.class);
    }

    @Test
    void addUserToLists() {
        String mockMessageResponse = "User added to lists success.";
        UserToListsRequest listsRequest = ListsServiceTestHelper.mockUserToListsRequest();
        when(listsService.addUserToLists(listsRequest)).thenReturn(mockMessageResponse);
        assertEquals(mockMessageResponse, listsMapper.addUserToLists(listsRequest));
        verify(listsService, times(1)).addUserToLists(listsRequest);
    }

    @Test
    void addUserToList() {
        when(listsService.addUserToList(1L, TestConstants.LIST_ID)).thenReturn(true);
        assertTrue(listsMapper.addUserToList(1L, TestConstants.LIST_ID));
        verify(listsService, times(1)).addUserToList(1L, TestConstants.LIST_ID);
    }

    @Test
    void getTweetsByListId() {
        HeaderResponse<TweetResponse> headerResponse = new HeaderResponse<>(
                List.of(new TweetResponse(), new TweetResponse()), new HttpHeaders());
        Pageable pageable = PageRequest.of(0, 20);
        when(listsService.getTweetsByListId(TestConstants.LIST_ID, pageable)).thenReturn(headerResponse);
        assertEquals(headerResponse, listsMapper.getTweetsByListId(TestConstants.LIST_ID, pageable));
        verify(listsService, times(1)).getTweetsByListId(TestConstants.LIST_ID, pageable);
    }

    @Test
    void getListDetails() {
        BaseListProjection mockBaseListProjection = ListsServiceTestHelper.createMockBaseListProjection(1L);
        BaseListResponse baseListResponse = getMockBaseListResponse();
        when(listsService.getListDetails(TestConstants.LIST_ID)).thenReturn(mockBaseListProjection);
        when(basicMapper.convertToResponse(mockBaseListProjection, BaseListResponse.class)).thenReturn(baseListResponse);
        assertEquals(baseListResponse, listsMapper.getListDetails(TestConstants.LIST_ID));
        verify(listsService, times(1)).getListDetails(TestConstants.LIST_ID);
        verify(basicMapper, times(1)).convertToResponse(mockBaseListProjection, BaseListResponse.class);
    }

    @Test
    void getListFollowers() {
        List<ListMemberResponse> mockListMemberResponses = ListsServiceTestHelper.createMockListMemberResponseList();
        when(listsService.getListFollowers(TestConstants.LIST_ID, 1L)).thenReturn(mockListMemberResponses);
        assertEquals(mockListMemberResponses, listsMapper.getListFollowers(TestConstants.LIST_ID, 1L));
        verify(listsService, times(1)).getListFollowers(TestConstants.LIST_ID, 1L);
    }

    @Test
    void getListMembers() {
        List<ListMemberResponse> mockListMemberResponses = ListsServiceTestHelper.createMockListMemberResponseList();
        when(listsService.getListMembers(TestConstants.LIST_ID, 1L)).thenReturn(mockListMemberResponses);
        assertEquals(mockListMemberResponses, listsMapper.getListMembers(TestConstants.LIST_ID, 1L));
        verify(listsService, times(1)).getListMembers(TestConstants.LIST_ID, 1L);
    }

    @Test
    void searchListMembersByUsername() {
        List<ListMemberResponse> mockListMemberResponses = ListsServiceTestHelper.createMockListMemberResponseList();
        when(listsService.searchListMembersByUsername(TestConstants.LIST_ID, "test_search")).thenReturn(mockListMemberResponses);
        assertEquals(mockListMemberResponses, listsMapper.searchListMembersByUsername(TestConstants.LIST_ID, "test_search"));
        verify(listsService, times(1)).searchListMembersByUsername(TestConstants.LIST_ID, "test_search");
    }

    private List<ListResponse> getMockListsResponses() {
        ListResponse listResponse1 = ListResponse.builder()
                .id(1L)
                .name(TestConstants.LIST_NAME)
                .description(TestConstants.LIST_DESCRIPTION)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .wallpaper("")
                .listOwner(new CommonUserResponse())
                .isFollower(false)
                .isListPinned(false)
                .build();
        ListResponse listResponse2 = ListResponse.builder()
                .id(2L)
                .name(TestConstants.LIST_NAME_2)
                .description(TestConstants.LIST_DESCRIPTION)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .wallpaper("")
                .listOwner(new CommonUserResponse())
                .isFollower(false)
                .isListPinned(false)
                .build();
        return Arrays.asList(listResponse1, listResponse2);
    }

    private List<ListUserResponse> getMockListUserResponses() {
        ListUserResponse listResponse1 = ListUserResponse.builder()
                .id(1L)
                .name(TestConstants.LIST_NAME)
                .description(TestConstants.LIST_DESCRIPTION)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .wallpaper("")
                .listOwner(new CommonUserResponse())
                .isPrivate(false)
                .isListPinned(false)
                .build();
        ListUserResponse listResponse2 = ListUserResponse.builder()
                .id(2L)
                .name(TestConstants.LIST_NAME_2)
                .description(TestConstants.LIST_DESCRIPTION)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .wallpaper("")
                .listOwner(new CommonUserResponse())
                .isPrivate(false)
                .isListPinned(false)
                .build();
        return Arrays.asList(listResponse1, listResponse2);
    }

    private List<PinnedListResponse> getMockPinnedListResponses() {
        PinnedListResponse listResponse1 = PinnedListResponse.builder()
                .id(1L)
                .name(TestConstants.LIST_NAME)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .wallpaper("")
                .isPrivate(false)
                .isListPinned(false)
                .build();
        PinnedListResponse listResponse2 = PinnedListResponse.builder()
                .id(2L)
                .name(TestConstants.LIST_NAME_2)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .wallpaper("")
                .isPrivate(false)
                .isListPinned(false)
                .build();
        return Arrays.asList(listResponse1, listResponse2);
    }

    private BaseListResponse getMockBaseListResponse() {
        return BaseListResponse.builder()
                .id(TestConstants.LIST_ID)
                .name(TestConstants.LIST_NAME)
                .description(TestConstants.LIST_DESCRIPTION)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .wallpaper("")
                .listOwner(new CommonUserResponse())
                .isFollower(false)
                .isPrivate(false)
                .followersSize(111L)
                .membersSize(111L)
                .build();
    }

    private ListsRequest getMockListsRequest() {
        return ListsRequest.builder()
                .id(TestConstants.LIST_ID)
                .name(TestConstants.LIST_NAME)
                .description(TestConstants.LIST_DESCRIPTION)
                .isPrivate(false)
                .listOwnerId(2L)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .wallpaper("")
                .build();
    }
}