package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Lists;

import java.util.List;

public interface ListsService {

    List<Lists> getAllTweetLists();

    List<Lists> getUserTweetLists();

    List<Lists> getUserPinnedLists();

    Lists getListById(Long listId);

    Lists createTweetList(Lists lists);

    List<Lists> getUserTweetListsById(Long userId);

    List<Lists> getTweetListsWhichUserIn();

    Lists editTweetList(Lists lists);

    String deleteList(Long listId);

    Lists followList(Long listId);

    Lists pinList(Long listId);

    List<Lists> addUserToLists(Long userId, List<Lists> lists);

    Lists addUserToList(Long userId, Long listId);
}
