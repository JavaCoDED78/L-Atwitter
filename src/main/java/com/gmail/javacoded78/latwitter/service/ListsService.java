package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Lists;
import com.gmail.javacoded78.latwitter.repository.projection.TweetProjection;
import com.gmail.javacoded78.latwitter.repository.projection.lists.*;

import java.util.List;
import java.util.Map;

public interface ListsService {

    List<ListProjection> getAllTweetLists();

    List<ListUserProjection> getUserTweetLists();

    List<PinnedListProjection> getUserPinnedLists();

    BaseListProjection getListById(Long listId);

    ListUserProjection createTweetList(Lists lists);

    List<ListProjection> getUserTweetListsById(Long userId);

    List<ListProjection> getTweetListsWhichUserIn();

    BaseListProjection editTweetList(Lists lists);

    String deleteList(Long listId);

    Boolean followList(Long listId);

    Boolean pinList(Long listId);

    List<Long> addUserToLists(Long userId, List<Lists> lists);

    Boolean addUserToList(Long userId, Long listId);

    List<TweetProjection> getTweetsByListId(Long listId);

    BaseListProjection getListDetails(Long listId);

    Map<String, Object> getListMembers(Long listId, Long listOwnerId);

    List<Map<String, Object>> searchListMembersByUsername(Long listId, String username);
}
