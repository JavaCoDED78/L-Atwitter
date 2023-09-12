package com.gmail.javacoded78.integration.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.ListsRequest;
import com.gmail.javacoded78.dto.request.UserToListsRequest;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_LIST_NAME_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.LIST_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.LIST_OWNER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_BLOCKED;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.PathConstants.ADD_USER;
import static com.gmail.javacoded78.constants.PathConstants.ADD_USER_LIST_ID;
import static com.gmail.javacoded78.constants.PathConstants.ADD_USER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOW_LIST_ID;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID_DETAILS;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID_FOLLOWERS;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID_MEMBERS;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.PINED;
import static com.gmail.javacoded78.constants.PathConstants.PIN_LIST_ID;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_LIST_ID;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_LISTS;
import static com.gmail.javacoded78.constants.PathConstants.USER;
import static com.gmail.javacoded78.constants.PathConstants.USER_CONSIST;
import static com.gmail.javacoded78.constants.PathConstants.USER_USER_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class ListsControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;
    private UserToListsRequest userToListsRequest;

    public void createUserToList(Long userId) {
        userToListsRequest = UserToListsRequest.builder()
                .userId(userId)
                .lists(
                        Arrays.asList(
                                new UserToListsRequest.ListsRequest(54L, true),
                                new UserToListsRequest.ListsRequest(56L, true)
                        )
                )
                .build();
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists - Get all tweet lists")
    void getAllTweetLists() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(7)),
                        jsonPath("$[0].id").value(54L),
                        jsonPath("$[0].name").value(TestConstants.LIST_NAME),
                        jsonPath("$[0].description").value(TestConstants.LIST_DESCRIPTION),
                        jsonPath("$[0].altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$[0].wallpaper").isEmpty(),
                        jsonPath("$[0].listOwner.id").value(TestConstants.LIST_USER_ID),
                        jsonPath("$[0].isFollower").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/user - Get user tweet lists")
    void getUserTweetLists() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + USER)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(3)),
                        jsonPath("$[0].id").isNotEmpty(),
                        jsonPath("$[0].name").value(TestConstants.LIST_NAME),
                        jsonPath("$[0].description").value(TestConstants.LIST_DESCRIPTION),
                        jsonPath("$[0].altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$[0].wallpaper").isEmpty(),
                        jsonPath("$[0].listOwner.id").value(TestConstants.LIST_USER_ID),
                        jsonPath("$[0].isPrivate").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/user/2 - Get user tweet lists by id")
    void getUserTweetListsById() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + USER_USER_ID, TestConstants.USER_ID)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(54L),
                        jsonPath("$[0].name").value(TestConstants.LIST_NAME),
                        jsonPath("$[0].description").value(TestConstants.LIST_DESCRIPTION),
                        jsonPath("$[0].altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$[0].wallpaper").isEmpty(),
                        jsonPath("$[0].listOwner.id").value(TestConstants.LIST_USER_ID),
                        jsonPath("$[0].isFollower").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/user/consist - Get tweet lists which user in")
    void getTweetListsWhichUserIn() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + USER_CONSIST)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(57L),
                        jsonPath("$[0].name").value(TestConstants.LIST_NAME),
                        jsonPath("$[0].description").value(TestConstants.LIST_DESCRIPTION),
                        jsonPath("$[0].altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$[0].wallpaper").isEmpty(),
                        jsonPath("$[0].listOwner.id").value(1L),
                        jsonPath("$[0].isFollower").value(true)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/pined - Get user pinned tweet lists")
    void getUserPinnedLists() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + PINED)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(2)),
                        jsonPath("$[0].id").value(54L),
                        jsonPath("$[0].name").value(TestConstants.LIST_NAME),
                        jsonPath("$[0].altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$[0].wallpaper").isEmpty(),
                        jsonPath("$[0].isPrivate").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/54 - Get list by id")
    void getListById() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID, 54)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(54),
                        jsonPath("$.name").value(TestConstants.LIST_NAME),
                        jsonPath("$.description").value(TestConstants.LIST_DESCRIPTION),
                        jsonPath("$.altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$.wallpaper").isEmpty(),
                        jsonPath("$.listOwner.id").value(TestConstants.LIST_USER_ID),
                        jsonPath("$.membersSize").value(1L),
                        jsonPath("$.followersSize").value(1L),
                        jsonPath("$.isPrivate").value(false),
                        jsonPath("$.isFollower").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/56 - Get owner private list by id")
    void getOwnerPrivateListById() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID, 56)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(56),
                        jsonPath("$.name").value(TestConstants.LIST_NAME),
                        jsonPath("$.description").value(TestConstants.LIST_DESCRIPTION),
                        jsonPath("$.altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$.wallpaper").isEmpty(),
                        jsonPath("$.listOwner.id").value(TestConstants.LIST_USER_ID),
                        jsonPath("$.membersSize").value(0L),
                        jsonPath("$.followersSize").value(0L),
                        jsonPath("$.isPrivate").value(true),
                        jsonPath("$.isFollower").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/57 - Get followed private list by id")
    void getFollowedPrivateListById() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID, 57)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(57),
                        jsonPath("$.name").value(TestConstants.LIST_NAME),
                        jsonPath("$.description").value(TestConstants.LIST_DESCRIPTION),
                        jsonPath("$.altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$.wallpaper").isEmpty(),
                        jsonPath("$.listOwner.id").value(1L),
                        jsonPath("$.membersSize").value(1L),
                        jsonPath("$.followersSize").value(1L),
                        jsonPath("$.isPrivate").value(true),
                        jsonPath("$.isFollower").value(true)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/58 - Not found existing private list by id")
    void getPrivateListById_NotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID, 58)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/99999 - Not found list by id")
    void getListById_NotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + "/99999")
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/lists/10 - Should validate is list author blocked my profile")
    void getBlockedUserByListId() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID, 60)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(String.format(USER_ID_BLOCKED, 2))));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/61 - Should validate is list author have private profile")
    void getPrivateUserProfileByListId() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID, 61)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/lists - Create Tweet List")
    void createTweetList() throws Exception {
        ListsRequest listsRequest = ListsRequest.builder()
                .name(TestConstants.LIST_NAME)
                .listOwnerId(TestConstants.USER_ID)
                .description(TestConstants.LIST_DESCRIPTION)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .build();

        mockMvc.perform(post(UI_V1_LISTS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(listsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name").value(TestConstants.LIST_NAME),
                        jsonPath("$.description").value(TestConstants.LIST_DESCRIPTION),
                        jsonPath("$.altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$.wallpaper").isEmpty(),
                        jsonPath("$.listOwner.id").value(TestConstants.LIST_USER_ID),
                        jsonPath("$.isPrivate").value(false)
                );
    }

    @Test
    @DisplayName("[400] POST /ui/v1/lists - Should list name length is 0")
    void createTweetList_ShouldListNameLengthIs0Symbols() throws Exception {
        ListsRequest listsRequest = ListsRequest.builder()
                .name("")
                .description(TestConstants.LIST_DESCRIPTION)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .build();

        mockMvc.perform(post(UI_V1_LISTS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(listsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_LIST_NAME_LENGTH)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/lists - Should list name length more than 25 symbols")
    void createTweetList_ShouldListNameLengthMoreThan25Symbols() throws Exception {
        ListsRequest listsRequest = ListsRequest.builder()
                .name(TestConstants.LIST_NAME)
                .description(TestConstants.LIST_DESCRIPTION)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .build();

        mockMvc.perform(post(UI_V1_LISTS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(listsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_LIST_NAME_LENGTH)));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/lists - Should list owner Not Found")
    void createTweetList_ShouldValidateListOwner() throws Exception {
        ListsRequest listsRequest = ListsRequest.builder()
                .name(TestConstants.LIST_NAME)
                .listOwnerId(1L)
                .description(TestConstants.LIST_DESCRIPTION)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .build();

        mockMvc.perform(post(UI_V1_LISTS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(listsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_OWNER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/lists - Edit Tweet List")
    void editTweetList() throws Exception {
        ListsRequest listsRequest = ListsRequest.builder()
                .name("edited name")
                .listOwnerId(2L)
                .id(54L)
                .description("edited description")
                .build();

        mockMvc.perform(put(UI_V1_LISTS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(listsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(54),
                        jsonPath("$.name").value("edited name"),
                        jsonPath("$.description").value("edited description"),
                        jsonPath("$.altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$.wallpaper").isEmpty(),
                        jsonPath("$.listOwner.id").value(TestConstants.LIST_USER_ID),
                        jsonPath("$.membersSize").value(1L),
                        jsonPath("$.followersSize").value(1L),
                        jsonPath("$.isPrivate").value(false),
                        jsonPath("$.isFollower").value(false)
                );
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/lists - Should list name length is 0")
    void editTweetList_ShouldListNameLengthIs0() throws Exception {
        ListsRequest listsRequest = ListsRequest.builder()
                .name("")
                .id(54L)
                .description("edited description")
                .build();

        mockMvc.perform(put(UI_V1_LISTS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(listsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_LIST_NAME_LENGTH)));
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/lists - Should list name length more than 25 symbols")
    void editTweetList_ShouldListNameLengthMoreThan25Symbols() throws Exception {
        ListsRequest listsRequest = ListsRequest.builder()
                .name(TestConstants.LINK_DESCRIPTION)
                .id(54L)
                .description("edited description")
                .build();

        mockMvc.perform(put(UI_V1_LISTS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(listsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_LIST_NAME_LENGTH)));
    }

    @Test
    @DisplayName("[404] PUT /ui/v1/lists - Should list Not Found")
    void editTweetList_ShouldListNotFound() throws Exception {
        ListsRequest listsRequest = ListsRequest.builder()
                .name("edited name")
                .listOwnerId(TestConstants.USER_ID)
                .id(99L)
                .description("edited description")
                .build();

        mockMvc.perform(put(UI_V1_LISTS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(listsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] PUT /ui/v1/lists - Should list owner Not Found")
    void editTweetList_ShouldListOwnerNotFound() throws Exception {
        ListsRequest listsRequest = ListsRequest.builder()
                .name("edited name")
                .listOwnerId(3L)
                .id(55L)
                .description("edited description")
                .build();

        mockMvc.perform(put(UI_V1_LISTS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(listsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_OWNER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] DELETE /ui/v1/lists/54 - Delete list")
    void deleteList() throws Exception {
        mockMvc.perform(delete(UI_V1_LISTS + LIST_ID, 54)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("List id:54 deleted.")));
    }

    @Test
    @DisplayName("[404] DELETE /ui/v1/lists/99 - Delete list Should Not found")
    void deleteList_ShouldNotFound() throws Exception {
        mockMvc.perform(delete(UI_V1_LISTS + "/99")
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] DELETE /ui/v1/lists/55 - Delete another user list Should Not found")
    void deleteAnotherUserList_ShouldNotFound() throws Exception {
        mockMvc.perform(delete(UI_V1_LISTS + LIST_ID, 55)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_OWNER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/follow/9 - Follow list")
    void followList() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + FOLLOW_LIST_ID, 59)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(59),
                        jsonPath("$.name").value(TestConstants.LIST_NAME),
                        jsonPath("$.description").value(TestConstants.LIST_DESCRIPTION),
                        jsonPath("$.altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$.wallpaper").isEmpty(),
                        jsonPath("$.listOwner.id").value(1),
                        jsonPath("$.isPrivate").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/follow/99 - Should List Not Found by id")
    void followList_ShouldListNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + FOLLOW_LIST_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/follow/58 - Should follow to private List Not Found by id")
    void followList_ShouldFollowToPrivateListNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + FOLLOW_LIST_ID, 58)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/follow/54 - Unfollow list")
    void unfollowList() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + FOLLOW_LIST_ID, 54)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(54),
                        jsonPath("$.name").value(TestConstants.LIST_NAME),
                        jsonPath("$.description").value(TestConstants.LIST_DESCRIPTION),
                        jsonPath("$.altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$.wallpaper").isEmpty(),
                        jsonPath("$.listOwner.id").value(2),
                        jsonPath("$.isPrivate").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/pin/56 - Pin list")
    void pinList() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + PIN_LIST_ID, 56)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(56),
                        jsonPath("$.name").value(TestConstants.LIST_NAME),
                        jsonPath("$.altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$.wallpaper").isEmpty(),
                        jsonPath("$.isPrivate").value(true)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/pin/54 - Unpin list")
    void unpinList() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + PIN_LIST_ID, 54)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(54),
                        jsonPath("$.name").value(TestConstants.LIST_NAME),
                        jsonPath("$.altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$.wallpaper").isEmpty(),
                        jsonPath("$.isPrivate").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/pin/58 - Should pinned list Not Found by id")
    void pinList_ShouldPinnedListNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + PIN_LIST_ID, 58)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/add/user/51 - Get lists to add user")
    void getListsToAddUser() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + ADD_USER_USER_ID, 51)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(2)),
                        jsonPath("$[0].id", Matchers.oneOf(54, 56)),
                        jsonPath("$[0].name").value(TestConstants.LIST_NAME),
                        jsonPath("$[0].altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$[0].wallpaper").isEmpty(),
                        jsonPath("$[0].isMemberInList").value(true),
                        jsonPath("$[0].isPrivate").value(false)
                );
    }

    @Test
    @DisplayName("[200] POST /ui/v1/lists/add/user - Add users to lists")
    void addUserToLists() throws Exception {
        createUserToList(1L);

        mockMvc.perform(post(UI_V1_LISTS + ADD_USER)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(userToListsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User added to lists success."));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/lists/add/user - Should user is blocked Add users to lists")
    void addUserToLists_ShouldUserIsBlocked() throws Exception {
        createUserToList(4L);

        mockMvc.perform(post(UI_V1_LISTS + ADD_USER)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(userToListsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(String.format(USER_ID_BLOCKED, 4))));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/lists/add/user - Should user have a private profile")
    void addUserToLists_ShouldUserHavePrivateProfile() throws Exception {
        createUserToList(3L);

        mockMvc.perform(post(UI_V1_LISTS + ADD_USER)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(userToListsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/lists/add/user - Should user blocked my profile")
    void addUserToLists_ShouldUserBlockedMyProfile() throws Exception {
        createUserToList(6L);

        mockMvc.perform(post(UI_V1_LISTS + ADD_USER)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(userToListsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(String.format(USER_ID_BLOCKED, 2))));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/lists/add/user/1/6 - Add user to list")
    void addUserToList() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + ADD_USER_LIST_ID, 1, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/lists/add/user/4/6 - Should user is blocked Add users to list")
    void addUserToList_ShouldUserIsBlocked() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + ADD_USER_LIST_ID, 4, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(String.format(USER_ID_BLOCKED, 4))));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/lists/add/user/3/6 - Should user have a private profile")
    void addUserToList_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + ADD_USER_LIST_ID, 3, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/lists/add/user/6/6 - Should user blocked my profile")
    void addUserToList_ShouldUserBlockedMyProfile() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + ADD_USER_LIST_ID, 6, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(String.format(USER_ID_BLOCKED, 2))));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/lists/add/user/7/99 - Should list Not Found")
    void addUserToList_ShouldListNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + ADD_USER_LIST_ID, 7, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/add/user/1/4 - Remove user from list")
    void removeUserFromList() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + ADD_USER_LIST_ID, 1, 4)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/54/tweets - Get tweets by list id")
    void getTweetsByListId() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_TWEETS, 54)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(45L),
                        jsonPath("$[0].text").value("media tweet test"),
                        jsonPath("$[0].dateTime").value("2021-10-03T20:38:51"),
                        jsonPath("$[0].scheduledDate").isEmpty(),
                        jsonPath("$[0].addressedUsername").isEmpty(),
                        jsonPath("$[0].addressedId").isEmpty(),
                        jsonPath("$[0].addressedTweetId").isEmpty(),
                        jsonPath("$[0].replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$[0].link").isEmpty(),
                        jsonPath("$[0].linkTitle").isEmpty(),
                        jsonPath("$[0].linkDescription").isEmpty(),
                        jsonPath("$[0].linkCover").isEmpty(),
                        jsonPath("$[0].linkCoverSize").isEmpty(),
                        jsonPath("$[0].user.id").value(1L),
                        jsonPath("$[0].images[0].id").value(1L),
                        jsonPath("$[0].quoteTweet.id").value(40L),
                        jsonPath("$[0].poll").isEmpty(),
                        jsonPath("$[0].retweetsCount").value(1L),
                        jsonPath("$[0].likedTweetsCount").value(1L),
                        jsonPath("$[0].repliesCount").value(0L),
                        jsonPath("$[0].isTweetLiked").value(true),
                        jsonPath("$[0].isTweetRetweeted").value(true),
                        jsonPath("$[0].isUserFollowByOtherUser").value(true),
                        jsonPath("$[0].isTweetDeleted").value(false),
                        jsonPath("$[0].isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/55/tweets - Get tweets by other user private list id")
    void getTweetsByUserPrivateListId() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_TWEETS, 55)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/54/details - Get list details")
    void getListDetails() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_DETAILS, 54)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(54),
                        jsonPath("$.name").value(TestConstants.LIST_NAME),
                        jsonPath("$.description").value(TestConstants.LIST_DESCRIPTION),
                        jsonPath("$.altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$.wallpaper").isEmpty(),
                        jsonPath("$.listOwner.id").value(TestConstants.LIST_USER_ID),
                        jsonPath("$.membersSize").value(1L),
                        jsonPath("$.followersSize").value(1L),
                        jsonPath("$.isPrivate").value(false),
                        jsonPath("$.isFollower").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/55/details - Get list details by other user private list")
    void getListDetailsByUserPrivateList() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_DETAILS, 55)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/54/2/followers - Get list followers")
    void getListFollowers() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_FOLLOWERS, 54, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(1L),
                        jsonPath("$[0].fullName").value(TestConstants.USERNAME2),
                        jsonPath("$[0].username").value(TestConstants.USERNAME2),
                        jsonPath("$[0].about").value(TestConstants.ABOUT2),
                        jsonPath("$[0].avatar").value(TestConstants.AVATAR_SRC_2),
                        jsonPath("$[0].isPrivateProfile").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/99/2/followers - Get list followers should list Not Found")
    void getListFollowers_ShouldListNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_FOLLOWERS, 99, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/55/1/followers - Get list followers by private list should list Not Found")
    void getListFollowers_ByPrivateListShouldListNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_FOLLOWERS, 55, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/lists/60/5/followers - Get list followers by blocked user")
    void getListFollowers_ByBlockedUserAndListNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_FOLLOWERS, 60, 5)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(String.format(USER_ID_BLOCKED, 2))));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/54/2/members - Get list members")
    void getListMembers() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_MEMBERS, 54, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(1L),
                        jsonPath("$[0].fullName").value(TestConstants.USERNAME2),
                        jsonPath("$[0].username").value(TestConstants.USERNAME2),
                        jsonPath("$[0].about").value(TestConstants.ABOUT2),
                        jsonPath("$[0].avatar").value(TestConstants.AVATAR_SRC_2),
                        jsonPath("$[0].isPrivateProfile").value(false),
                        jsonPath("$[0].isMemberInList").value(true)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/59/1/members - Get list members by another user")
    void getListMembersByAnotherUser() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_MEMBERS, 59, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(1L),
                        jsonPath("$[0].fullName").value(TestConstants.USERNAME2),
                        jsonPath("$[0].username").value(TestConstants.USERNAME2),
                        jsonPath("$[0].about").value(TestConstants.ABOUT2),
                        jsonPath("$[0].avatar").value(TestConstants.AVATAR_SRC_2),
                        jsonPath("$[0].isPrivateProfile").value(false)
                        );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/99/2/members - Get list members should list Not Found")
    void getListMembers_ShouldListNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_MEMBERS, 99, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/lists/55/1/members - Get list members by private list should list Not Found")
    void getListMembers_ByPrivateListShouldListNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_MEMBERS, 55, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(LIST_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/lists/60/5/members - Get list members by blocked user")
    void getListMembers_ByBlockedUserAndListNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + LIST_ID_MEMBERS, 60, 5)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(String.format(USER_ID_BLOCKED, 2))));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/lists/search/2/MrCat - Search list members by username")
    void searchListMembersByUsername() throws Exception {
        mockMvc.perform(get(UI_V1_LISTS + SEARCH_LIST_ID, 2, TestConstants.USERNAME)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(6)))
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].fullName").value(TestConstants.USERNAME))
                .andExpect(jsonPath("$[0].username").value(TestConstants.USERNAME))
                .andExpect(jsonPath("$[0].about").value(TestConstants.ABOUT))
                .andExpect(jsonPath("$[0].avatar").value(TestConstants.AVATAR_SRC_1))
                .andExpect(jsonPath("$[0].isPrivateProfile").isNotEmpty())
                .andExpect(jsonPath("$[0].isMemberInList").isNotEmpty());
    }
}
