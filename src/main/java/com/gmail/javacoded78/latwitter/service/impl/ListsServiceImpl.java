package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.exception.ApiRequestException;
import com.gmail.javacoded78.latwitter.model.Lists;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.ImageRepository;
import com.gmail.javacoded78.latwitter.repository.ListsRepository;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.service.AuthenticationService;
import com.gmail.javacoded78.latwitter.service.ListsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListsServiceImpl implements ListsService {

    private final AuthenticationService authenticationService;
    private final ListsRepository listsRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Override
    public List<Lists> getAllTweetLists() {
        return listsRepository.findByIsPrivateFalse();
    }

    @Override
    public List<Lists> getUserTweetLists() {
        User user = authenticationService.getAuthenticatedUser();
        return user.getUserLists();
    }

    @Override
    public List<Lists> getUserPinnedLists() {
        User user = authenticationService.getAuthenticatedUser();
        return user.getUserLists().stream()
                .filter(list -> list.getPinnedDate() != null)
                .sorted(Comparator.comparing(Lists::getPinnedDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Lists getListById(Long listId) {
        User user = authenticationService.getAuthenticatedUser();
        Lists list = listsRepository.getListById(listId, user.getId())
                .orElseThrow(() -> new ApiRequestException("List not found", HttpStatus.NOT_FOUND));
        list.setTweets(mergeTweets(list));
        return list;
    }

    @Override
    public Lists createTweetList(Lists lists) {
        if (lists.getName() == null) {
            throw new ApiRequestException("List name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        User user = authenticationService.getAuthenticatedUser();
        lists.setListOwner(user);
        Lists userTweetList = listsRepository.save(lists);
        List<Lists> userLists = user.getUserLists();
        userLists.add(userTweetList);
        userRepository.save(user);
        return userTweetList;
    }

    @Override
    public List<Lists> getUserTweetListsById(Long userId) {
        return listsRepository.findByListOwner_IdAndIsPrivateFalse(userId);
    }

    @Override
    public List<Lists> getTweetListsWhichUserIn() {
        User user = authenticationService.getAuthenticatedUser();
        return listsRepository.findByMembers_Id(user.getId());
    }

    @Override
    public Lists editTweetList(Lists listInfo) {
        if (listInfo.getName() == null) {
            throw new ApiRequestException("List name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        User user = authenticationService.getAuthenticatedUser();

        if (!listInfo.getListOwner().getId().equals(user.getId())) {
            throw new ApiRequestException("List owner not found", HttpStatus.BAD_REQUEST);
        }
        Lists listFromDb = listsRepository.getOne(listInfo.getId());
        listFromDb.setName(listInfo.getName());
        listFromDb.setDescription(listInfo.getDescription());
        listFromDb.setWallpaper(listInfo.getWallpaper());
        listFromDb.setPrivate(listInfo.isPrivate());
        Lists list = listsRepository.save(listFromDb);
        list.setTweets(mergeTweets(list));
        return list;
    }

    @Override
    public String deleteList(Long listId) {
        User user = authenticationService.getAuthenticatedUser();
        Lists list = listsRepository.findById(listId)
                .orElseThrow(() -> new ApiRequestException("List not found", HttpStatus.NOT_FOUND));

        if (!list.getListOwner().getId().equals(user.getId())) {
            throw new ApiRequestException("List owner not found", HttpStatus.BAD_REQUEST);
        }
        list.getTweets().removeAll(list.getTweets());
        list.getMembers().removeAll(list.getMembers());
        list.getFollowers().removeAll(list.getFollowers());

        if (list.getWallpaper() != null) {
            imageRepository.delete(list.getWallpaper());
        }
        user.getUserLists().remove(list);
        listsRepository.delete(list);
        return "List id:" + list.getId() + " deleted.";
    }

    @Override
    public Lists followList(Long listId) {
        User user = authenticationService.getAuthenticatedUser();
        Lists list = listsRepository.findByIdAndIsPrivateFalse(listId)
                .orElseThrow(() -> new ApiRequestException("List not found", HttpStatus.NOT_FOUND));
        // TODO if user blocked, can the user follow list???
        Optional<User> follower = list.getFollowers().stream()
                .filter(f -> f.equals(user))
                .findFirst();
        List<User> listFollowers = list.getFollowers();

        if (follower.isPresent()) {
            listFollowers.remove(follower.get());
            if (list.getPinnedDate() != null) {
                list.setPinnedDate(null);
            }
            user.getUserLists().remove(list);
        } else {
            listFollowers.add(user);
            user.getUserLists().add(list);
        }
        userRepository.save(user);
        return listsRepository.save(list);
    }

    @Override
    public Lists pinList(Long listId) {
        User user = authenticationService.getAuthenticatedUser();
        Lists list = user.getUserLists().stream()
                .filter(userList -> userList.getId().equals(listId))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException("List not found", HttpStatus.NOT_FOUND));

        if (list.getPinnedDate() == null) {
            list.setPinnedDate(LocalDateTime.now().withNano(0));
        } else {
            list.setPinnedDate(null);
        }
        return listsRepository.save(list);
    }

    @Override
    public List<Lists> addUserToLists(Long userId, List<Lists> lists) {
        User authUser = authenticationService.getAuthenticatedUser();
        authUser.getUserBlockedList().stream() // TODO check: if user in my block list [+]
                .filter(blockedUser -> !blockedUser.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException("User is blocked", HttpStatus.BAD_REQUEST));
        User user = userRepository.getValidUser(userId, authUser.getId()) // TODO check: if private profile, if blocked [+]
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        List<Lists> userLists = authUser.getUserLists();
        Set<Lists> commonLists = userLists.stream() // TODO check: if lists is exist in db [+]
                .distinct()
                .filter(lists::contains)
                .collect(Collectors.toSet());
        commonLists.forEach((list) -> {
            Optional<User> userInList = list.getMembers().stream()
                    .filter(m -> m.getId().equals(user.getId()))
                    .findFirst();

            userLists.forEach((userList) -> {
                Optional<User> memberInUserList = userList.getMembers().stream()
                        .filter(m -> m.getId().equals(user.getId()))
                        .findFirst();

                if (list.getId().equals(userList.getId())) {
                    if (userInList.isPresent() && memberInUserList.isEmpty()) {
                        userList.getMembers().add(user);
                        listsRepository.save(userList);
                    }
                    if (userInList.isEmpty() && memberInUserList.isPresent()) {
                        userList.getMembers().remove(user);
                        listsRepository.save(userList);
                    }
                }
            });
        });
        return userLists;
    }

    @Override
    public Lists addUserToList(Long userId, Long listId) {
        User authUser = authenticationService.getAuthenticatedUser();
        authUser.getUserBlockedList().stream()
                .filter(blockedUser -> !blockedUser.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException("User is blocked", HttpStatus.BAD_REQUEST));
        User user = userRepository.getValidUser(userId, authUser.getId())
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        Lists list = listsRepository.findById(listId)
                .orElseThrow(() -> new ApiRequestException("List not found", HttpStatus.NOT_FOUND));
        Optional<User> listMember = list.getMembers().stream()
                .filter(member -> member.getId().equals(user.getId()))
                .findFirst();

        if (listMember.isPresent()) {
            list.getMembers().remove(user);
        } else {
            list.getMembers().add(user);
        }
        listsRepository.save(list);
        list.setTweets(mergeTweets(list));
        return list;
    }

    private List<Tweet> mergeTweets(Lists list) {
        List<Tweet> tweets = list.getTweets();
        List<User> members = list.getMembers();
        members.forEach(member -> tweets.addAll(member.getTweets()));
        return tweets.stream()
                .filter(tweet -> tweet.getAddressedUsername() == null)
                .sorted(Comparator.comparing(Tweet::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}
