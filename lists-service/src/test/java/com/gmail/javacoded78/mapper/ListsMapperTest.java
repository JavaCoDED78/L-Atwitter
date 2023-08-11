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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ListsMapperTest {

    @Autowired
    private ListsMapper listsMapper;

    @MockBean
    private BasicMapper basicMapper;

    @MockBean
    private ListsService listsService;

    @Test
    public void getAllTweetLists() {
        List<ListProjection> mockListProjections = ListsServiceTestHelper.createMockListProjectionList();
        when(listsService.getAllTweetLists()).thenReturn(mockListProjections);
        when(basicMapper.convertToResponseList(mockListProjections, ListResponse.class))
                .thenReturn(getMockListsResponses());
        assertEquals(2, listsMapper.getAllTweetLists().size());
        verify(listsService, times(1)).getAllTweetLists();
        verify(basicMapper, times(1)).convertToResponseList(mockListProjections, ListResponse.class);
    }

    @Test
    public void getUserTweetLists() {
        List<ListUserProjection> mockListUserProjections = ListsServiceTestHelper.createMockListUserProjectionList();
        when(listsService.getUserTweetLists()).thenReturn(mockListUserProjections);
        when(basicMapper.convertToResponseList(mockListUserProjections, ListUserResponse.class))
                .thenReturn(getMockListUserResponses());
        assertEquals(2, listsMapper.getUserTweetLists().size());
        verify(listsService, times(1)).getUserTweetLists();
        verify(basicMapper, times(1)).convertToResponseList(mockListUserProjections, ListUserResponse.class);
    }

    @Test
    public void getUserTweetListsById() {
        List<ListProjection> mockListProjections = ListsServiceTestHelper.createMockListProjectionList();
        when(listsService.getUserTweetListsById(1L)).thenReturn(mockListProjections);
        when(basicMapper.convertToResponseList(mockListProjections, ListResponse.class))
                .thenReturn(getMockListsResponses());
        assertEquals(2, listsMapper.getUserTweetListsById(1L).size());
        verify(listsService, times(1)).getUserTweetListsById(1L);
        verify(basicMapper, times(1)).convertToResponseList(mockListProjections, ListResponse.class);
    }

    @Test
    public void getTweetListsWhichUserIn() {
        List<ListProjection> mockListProjections = ListsServiceTestHelper.createMockListProjectionList();
        when(listsService.getTweetListsWhichUserIn()).thenReturn(mockListProjections);
        when(basicMapper.convertToResponseList(mockListProjections, ListResponse.class))
                .thenReturn(getMockListsResponses());
        assertEquals(2, listsMapper.getTweetListsWhichUserIn().size());
        verify(listsService, times(1)).getTweetListsWhichUserIn();
        verify(basicMapper, times(1)).convertToResponseList(mockListProjections, ListResponse.class);
    }

    @Test
    public void getUserPinnedLists() {
        List<PinnedListProjection> mockPinnedListProjections = ListsServiceTestHelper.createMockPinnedListProjectionList();
        when(listsService.getUserPinnedLists()).thenReturn(mockPinnedListProjections);
        when(basicMapper.convertToResponseList(mockPinnedListProjections, PinnedListResponse.class))
                .thenReturn(getMockPinnedListResponses());
        assertEquals(2, listsMapper.getUserPinnedLists().size());
        verify(listsService, times(1)).getUserPinnedLists();
        verify(basicMapper, times(1)).convertToResponseList(mockPinnedListProjections, PinnedListResponse.class);
    }

    @Test
    public void getListById() {
        BaseListProjection mockBaseListProjection = ListsServiceTestHelper.createMockBaseListProjection(1L);
        BaseListResponse mockBaseListResponse = getMockBaseListResponse();
        when(listsService.getListById(TestConstants.LIST_ID)).thenReturn(mockBaseListProjection);
        when(basicMapper.convertToResponse(mockBaseListProjection, BaseListResponse.class)).thenReturn(mockBaseListResponse);
        assertEquals(mockBaseListResponse, listsMapper.getListById(TestConstants.LIST_ID));
        verify(listsService, times(1)).getListById(TestConstants.LIST_ID);
        verify(basicMapper, times(1)).convertToResponse(mockBaseListProjection, BaseListResponse.class);
    }

    @Test
    public void createTweetList() {
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
    public void editTweetList() {
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
    public void deleteList() {
        String mockMessageResponse = "List id:1 deleted.";
        when(listsService.deleteList(TestConstants.LIST_ID)).thenReturn(mockMessageResponse);
        assertEquals(mockMessageResponse, listsMapper.deleteList(TestConstants.LIST_ID));
        verify(listsService, times(1)).deleteList(TestConstants.LIST_ID);
    }

    @Test
    public void followList() {
        ListUserProjection mockListUserProjection = ListsServiceTestHelper.createMockListUserProjectionList().get(0);
        ListUserResponse listUserResponse = getMockListUserResponses().get(0);
        when(listsService.followList(TestConstants.LIST_ID)).thenReturn(mockListUserProjection);
        when(basicMapper.convertToResponse(mockListUserProjection, ListUserResponse.class)).thenReturn(listUserResponse);
        assertEquals(listUserResponse, listsMapper.followList(TestConstants.LIST_ID));
        verify(listsService, times(1)).followList(TestConstants.LIST_ID);
        verify(basicMapper, times(1)).convertToResponse(mockListUserProjection, ListUserResponse.class);
    }

    @Test
    public void pinList() {
        PinnedListProjection pinnedListProjection = ListsServiceTestHelper.createMockPinnedListProjectionList().get(0);
        PinnedListResponse pinnedListResponse = getMockPinnedListResponses().get(0);
        when(listsService.pinList(TestConstants.LIST_ID)).thenReturn(pinnedListProjection);
        when(basicMapper.convertToResponse(pinnedListProjection, PinnedListResponse.class)).thenReturn(pinnedListResponse);
        assertEquals(pinnedListResponse, listsMapper.pinList(TestConstants.LIST_ID));
        verify(listsService, times(1)).pinList(TestConstants.LIST_ID);
        verify(basicMapper, times(1)).convertToResponse(pinnedListProjection, PinnedListResponse.class);
    }

    @Test
    public void addUserToLists() {
        String mockMessageResponse = "User added to lists success.";
        UserToListsRequest listsRequest = ListsServiceTestHelper.mockUserToListsRequest();
        when(listsService.addUserToLists(listsRequest)).thenReturn(mockMessageResponse);
        assertEquals(mockMessageResponse, listsMapper.addUserToLists(listsRequest));
        verify(listsService, times(1)).addUserToLists(listsRequest);
    }

    @Test
    public void addUserToList() {
        when(listsService.addUserToList(1L, TestConstants.LIST_ID)).thenReturn(true);
        assertTrue(listsMapper.addUserToList(1L, TestConstants.LIST_ID));
        verify(listsService, times(1)).addUserToList(1L, TestConstants.LIST_ID);
    }

    @Test
    public void getTweetsByListId() {
        HeaderResponse<TweetResponse> headerResponse = new HeaderResponse<>(
                List.of(new TweetResponse(), new TweetResponse()), new HttpHeaders());
        Pageable pageable = PageRequest.of(0, 20);
        when(listsService.getTweetsByListId(TestConstants.LIST_ID, pageable)).thenReturn(headerResponse);
        assertEquals(headerResponse, listsMapper.getTweetsByListId(TestConstants.LIST_ID, pageable));
        verify(listsService, times(1)).getTweetsByListId(TestConstants.LIST_ID, pageable);
    }

    @Test
    public void getListDetails() {
        BaseListProjection mockBaseListProjection = ListsServiceTestHelper.createMockBaseListProjection(1L);
        BaseListResponse baseListResponse = getMockBaseListResponse();
        when(listsService.getListDetails(TestConstants.LIST_ID)).thenReturn(mockBaseListProjection);
        when(basicMapper.convertToResponse(mockBaseListProjection, BaseListResponse.class)).thenReturn(baseListResponse);
        assertEquals(baseListResponse, listsMapper.getListDetails(TestConstants.LIST_ID));
        verify(listsService, times(1)).getListDetails(TestConstants.LIST_ID);
        verify(basicMapper, times(1)).convertToResponse(mockBaseListProjection, BaseListResponse.class);
    }

    @Test
    public void getListFollowers() {
        List<ListMemberResponse> mockListMemberResponses = ListsServiceTestHelper.createMockListMemberResponseList();
        when(listsService.getListFollowers(TestConstants.LIST_ID, 1L)).thenReturn(mockListMemberResponses);
        assertEquals(mockListMemberResponses, listsMapper.getListFollowers(TestConstants.LIST_ID, 1L));
        verify(listsService, times(1)).getListFollowers(TestConstants.LIST_ID, 1L);
    }

    @Test
    public void getListMembers() {
        List<ListMemberResponse> mockListMemberResponses = ListsServiceTestHelper.createMockListMemberResponseList();
        when(listsService.getListMembers(TestConstants.LIST_ID, 1L)).thenReturn(mockListMemberResponses);
        assertEquals(mockListMemberResponses, listsMapper.getListMembers(TestConstants.LIST_ID, 1L));
        verify(listsService, times(1)).getListMembers(TestConstants.LIST_ID, 1L);
    }

    @Test
    public void searchListMembersByUsername() {
        List<ListMemberResponse> mockListMemberResponses = ListsServiceTestHelper.createMockListMemberResponseList();
        when(listsService.searchListMembersByUsername(TestConstants.LIST_ID, "test_search")).thenReturn(mockListMemberResponses);
        assertEquals(mockListMemberResponses, listsMapper.searchListMembersByUsername(TestConstants.LIST_ID, "test_search"));
        verify(listsService, times(1)).searchListMembersByUsername(TestConstants.LIST_ID, "test_search");
    }

    private List<ListResponse> getMockListsResponses() {
        ListResponse listResponse1 = new ListResponse();
        listResponse1.setId(1L);
        listResponse1.setName(TestConstants.LIST_NAME);
        listResponse1.setDescription(TestConstants.LIST_DESCRIPTION);
        listResponse1.setAltWallpaper(TestConstants.LIST_ALT_WALLPAPER);
        listResponse1.setWallpaper("");
        listResponse1.setListOwner(new CommonUserResponse());
        listResponse1.setFollower(false);
        listResponse1.setListPinned(false);
        ListResponse listResponse2 = new ListResponse();
        listResponse2.setId(2L);
        listResponse2.setName(TestConstants.LIST_NAME_2);
        listResponse2.setDescription(TestConstants.LIST_DESCRIPTION);
        listResponse2.setAltWallpaper(TestConstants.LIST_ALT_WALLPAPER);
        listResponse2.setWallpaper("");
        listResponse2.setListOwner(new CommonUserResponse());
        listResponse2.setFollower(false);
        listResponse2.setListPinned(false);
        return Arrays.asList(listResponse1, listResponse2);
    }

    private List<ListUserResponse> getMockListUserResponses() {
        ListUserResponse listResponse1 = new ListUserResponse();
        listResponse1.setId(1L);
        listResponse1.setName(TestConstants.LIST_NAME);
        listResponse1.setDescription(TestConstants.LIST_DESCRIPTION);
        listResponse1.setAltWallpaper(TestConstants.LIST_ALT_WALLPAPER);
        listResponse1.setWallpaper("");
        listResponse1.setListOwner(new CommonUserResponse());
        listResponse1.setPrivate(false);
        listResponse1.setListPinned(false);
        ListUserResponse listResponse2 = new ListUserResponse();
        listResponse2.setId(2L);
        listResponse2.setName(TestConstants.LIST_NAME_2);
        listResponse2.setDescription(TestConstants.LIST_ALT_WALLPAPER);
        listResponse2.setWallpaper("");
        listResponse2.setListOwner(new CommonUserResponse());
        listResponse2.setPrivate(false);
        listResponse2.setListPinned(false);
        return Arrays.asList(listResponse1, listResponse2);
    }

    private List<PinnedListResponse> getMockPinnedListResponses() {
        PinnedListResponse listResponse1 = new PinnedListResponse();
        listResponse1.setId(1L);
        listResponse1.setName(TestConstants.LIST_NAME);
        listResponse1.setAltWallpaper(TestConstants.LIST_ALT_WALLPAPER);
        listResponse1.setWallpaper("");
        listResponse1.setPrivate(false);
        listResponse1.setListPinned(false);
        PinnedListResponse listResponse2 = new PinnedListResponse();
        listResponse2.setId(2L);
        listResponse2.setName(TestConstants.LIST_NAME_2);
        listResponse2.setWallpaper("");
        listResponse2.setPrivate(false);
        listResponse2.setListPinned(false);
        return Arrays.asList(listResponse1, listResponse2);
    }

    private BaseListResponse getMockBaseListResponse() {
        BaseListResponse listResponse = new BaseListResponse();
        listResponse.setId(TestConstants.LIST_ID);
        listResponse.setName(TestConstants.LIST_NAME);
        listResponse.setDescription(TestConstants.LIST_DESCRIPTION);
        listResponse.setAltWallpaper(TestConstants.LIST_ALT_WALLPAPER);
        listResponse.setWallpaper("");
        listResponse.setListOwner(new CommonUserResponse());
        listResponse.setFollower(false);
        listResponse.setPrivate(false);
        listResponse.setFollowersSize(111L);
        listResponse.setMembersSize(111L);
        return listResponse;
    }

    private ListsRequest getMockListsRequest() {
        ListsRequest listsRequest = new ListsRequest();
        listsRequest.setId(TestConstants.LIST_ID);
        listsRequest.setName(TestConstants.LIST_NAME);
        listsRequest.setDescription(TestConstants.LIST_DESCRIPTION);
        listsRequest.setIsPrivate(false);
        listsRequest.setListOwnerId(2L);
        listsRequest.setAltWallpaper(TestConstants.LIST_ALT_WALLPAPER);
        listsRequest.setWallpaper("");
        return listsRequest;
    }
}