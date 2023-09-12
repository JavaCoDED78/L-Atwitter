package com.gmail.javacoded78.service.util;

import com.gmail.javacoded78.dto.request.UserToListsRequest;
import com.gmail.javacoded78.dto.response.lists.ListMemberResponse;
import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import com.gmail.javacoded78.model.Lists;
import com.gmail.javacoded78.repository.projection.BaseListProjection;
import com.gmail.javacoded78.repository.projection.ListProjection;
import com.gmail.javacoded78.repository.projection.ListUserProjection;
import com.gmail.javacoded78.repository.projection.PinnedListProjection;
import com.gmail.javacoded78.repository.projection.SimpleListProjection;
import com.gmail.javacoded78.repository.projection.TweetListProjection;
import com.gmail.javacoded78.util.TestConstants;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListsServiceTestHelper {

    private static final ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    public static List<ListProjection> createMockListProjectionList() {
        ListProjection list1 = factory.createProjection(
                ListProjection.class,
                Map.of(
                        "id", 1L,
                        "name", TestConstants.LIST_NAME,
                        "description", TestConstants.LIST_DESCRIPTION,
                        "altWallpaper", TestConstants.LIST_ALT_WALLPAPER,
                        "wallpaper", "",
                        "listOwnerId", TestConstants.LIST_USER_ID,
                        "listOwner", new CommonUserResponse(),
                        "isFollower", false,
                        "isListPinned", false));
        ListProjection list2 = factory.createProjection(
                ListProjection.class,
                Map.of(
                        "id", 2L,
                        "name", TestConstants.LIST_NAME_2,
                        "description", TestConstants.LIST_DESCRIPTION,
                        "altWallpaper", TestConstants.LIST_ALT_WALLPAPER,
                        "wallpaper", "",
                        "listOwnerId", TestConstants.LIST_USER_ID,
                        "listOwner", new CommonUserResponse(),
                        "isFollower", false,
                        "isListPinned", false));
        return Arrays.asList(list1, list2);
    }

    public static List<ListUserProjection> createMockListUserProjectionList() {
        ListUserProjection listUser1 = factory.createProjection(
                ListUserProjection.class,
                Map.of(
                        "id", 1L,
                        "name", TestConstants.LIST_NAME,
                        "description", TestConstants.LIST_DESCRIPTION,
                        "altWallpaper", TestConstants.LIST_ALT_WALLPAPER,
                        "wallpaper", "",
                        "listOwnerId", TestConstants.LIST_USER_ID,
                        "listOwner", new CommonUserResponse(),
                        "isListPinned", false,
                        "isPrivate", false));
        ListUserProjection listUser2 = factory.createProjection(
                ListUserProjection.class,
                Map.of(
                        "id", 2L,
                        "name", TestConstants.LIST_NAME_2,
                        "description", TestConstants.LIST_DESCRIPTION,
                        "altWallpaper", TestConstants.LIST_ALT_WALLPAPER,
                        "wallpaper", "",
                        "listOwnerId", TestConstants.LIST_USER_ID,
                        "listOwner", new CommonUserResponse(),
                        "isListPinned", false,
                        "isPrivate", false));
        return Arrays.asList(listUser1, listUser2);
    }

    public static List<PinnedListProjection> createMockPinnedListProjectionList() {
        PinnedListProjection pinnedList1 = factory.createProjection(
                PinnedListProjection.class,
                Map.of(
                        "id", 1L,
                        "name", TestConstants.LIST_NAME,
                        "altWallpaper", TestConstants.LIST_ALT_WALLPAPER,
                        "wallpaper", "",
                        "isListPinned", false,
                        "isPrivate", false));
        PinnedListProjection pinnedList2 = factory.createProjection(
                PinnedListProjection.class,
                Map.of(
                        "id", 2L,
                        "name", TestConstants.LIST_NAME_2,
                        "altWallpaper", TestConstants.LIST_ALT_WALLPAPER,
                        "wallpaper", "",
                        "isListPinned", false,
                        "isPrivate", false));
        return Arrays.asList(pinnedList1, pinnedList2);
    }

    public static List<SimpleListProjection> createMockSimpleListProjectionList() {
        SimpleListProjection simpleList1 = factory.createProjection(
                SimpleListProjection.class,
                Map.of(
                        "id", 1L,
                        "name", TestConstants.LIST_NAME,
                        "altWallpaper", TestConstants.LIST_ALT_WALLPAPER,
                        "wallpaper", "",
                        "isPrivate", false));
        SimpleListProjection simpleList2 = factory.createProjection(
                SimpleListProjection.class,
                Map.of(
                        "id", 2L,
                        "name", TestConstants.LIST_NAME_2,
                        "altWallpaper", TestConstants.LIST_ALT_WALLPAPER,
                        "wallpaper", "",
                        "isPrivate", false));
        return Arrays.asList(simpleList1, simpleList2);
    }

    public static List<ListMemberResponse> createMockListMemberResponseList() {
        ListMemberResponse listMember1 = ListMemberResponse.builder()
                .id(1L)
                .fullName("test name 1")
                .username("test username 1")
                .about("")
                .avatar("")
                .isMemberInList(false)
                .isPrivateProfile(false)
                .build();
        ListMemberResponse listMember2 = ListMemberResponse.builder()
                .id(2L)
                .fullName("test name 2")
                .username("test username 2")
                .about("")
                .avatar("")
                .isMemberInList(false)
                .isPrivateProfile(false)
                .build();
        return Arrays.asList(listMember1, listMember2);
    }

    public static BaseListProjection createMockBaseListProjection(Long listOwnerId) {
        Map<String, Object> baseListMap = new HashMap<>();
        baseListMap.put("id", TestConstants.LIST_ID);
        baseListMap.put("name", TestConstants.LIST_NAME);
        baseListMap.put("description", TestConstants.LIST_DESCRIPTION);
        baseListMap.put("altWallpaper", TestConstants.LIST_ALT_WALLPAPER);
        baseListMap.put("wallpaper", "");
        baseListMap.put("listOwnerId", listOwnerId);
        baseListMap.put("isPrivate", false);
        baseListMap.put("listOwner", new CommonUserResponse());
        baseListMap.put("membersSize", 111L);
        baseListMap.put("followersSize", 111L);
        baseListMap.put("isFollower", true);
        return factory.createProjection(BaseListProjection.class, baseListMap);
    }

    public static Lists createMockLists() {
        return Lists.builder()
                .id(TestConstants.LIST_ID)
                .name(TestConstants.LIST_NAME)
                .description(TestConstants.LIST_DESCRIPTION)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .wallpaper("")
                .listOwnerId(TestConstants.LIST_USER_ID)
                .build();
    }

    public static UserToListsRequest mockUserToListsRequest() {
        UserToListsRequest listsRequest = new UserToListsRequest();
        listsRequest.setUserId(1L);
        listsRequest.setLists(List.of(
                new UserToListsRequest.ListsRequest(1L, true),
                new UserToListsRequest.ListsRequest(2L, false)
        ));
        return listsRequest;
    }

    public static TweetListProjection mockTweetListProjection(Long userId) {
        return factory.createProjection(
                TweetListProjection.class,
                Map.of(
                        "id", TestConstants.LIST_ID,
                        "name", TestConstants.LIST_NAME,
                        "altWallpaper", TestConstants.LIST_ALT_WALLPAPER,
                        "wallpaper", "",
                        "listOwnerId", userId,
                        "listOwner", new CommonUserResponse(),
                        "isPrivate", false,
                        "membersSize", 1L
                ));
    }
}
