package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.exception.ApiRequestException;
import com.gmail.javacoded78.latwitter.model.Lists;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.ImageRepository;
import com.gmail.javacoded78.latwitter.repository.ListsRepository;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.repository.projection.TweetProjection;
import com.gmail.javacoded78.latwitter.repository.projection.TweetsProjection;
import com.gmail.javacoded78.latwitter.repository.projection.lists.*;
import com.gmail.javacoded78.latwitter.service.AuthenticationService;
import com.gmail.javacoded78.latwitter.service.ListsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListsServiceImpl implements ListsService {

    private final AuthenticationService authenticationService;
    private final ListsRepository listsRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Override
    public List<ListProjection> getAllTweetLists() {
        return listsRepository.getAllTweetLists().stream()
                .map(ListsProjection::getList)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListUserProjection> getUserTweetLists() {
        Long userId = authenticationService.getAuthenticatedUserId();
        return listsRepository.getUserTweetLists(userId).stream()
                .map(ListsUserProjection::getList)
                .collect(Collectors.toList());
    }

    @Override
    public List<PinnedListProjection> getUserPinnedLists() {
        Long userId = authenticationService.getAuthenticatedUserId();
        return listsRepository.getUserPinnedLists(userId).stream()
                .map(PinnedListsProjection::getList)
                .collect(Collectors.toList());
    }

    @Override
    public BaseListProjection getListById(Long listId) {
        Long userId = authenticationService.getAuthenticatedUserId();
        return listsRepository.getListById(listId, userId)
                .orElseThrow(() -> new ApiRequestException("List not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public ListUserProjection createTweetList(Lists lists) {
        if (lists.getName().length() == 0 || lists.getName().length() > 25) {
            throw new ApiRequestException("Incorrect list name length", HttpStatus.BAD_REQUEST);
        }
        User user = authenticationService.getAuthenticatedUser();
        lists.setListOwner(user);
        Lists userTweetList = listsRepository.save(lists);
        List<Lists> userLists = user.getUserLists();
        userLists.add(userTweetList);
        return listsRepository.getUserTweetListById(userTweetList.getId());
    }

    @Override
    public List<ListProjection> getUserTweetListsById(Long userId) { // TODO add tests
        return listsRepository.findByListOwnerIdAndIsPrivateFalse(userId).stream()
                .map(ListsProjection::getList)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListProjection> getTweetListsWhichUserIn() { // TODO add tests
        Long userId = authenticationService.getAuthenticatedUserId();
        return listsRepository.findByMembers_Id(userId).stream()
                .map(ListsProjection::getList)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BaseListProjection editTweetList(Lists listInfo) {
        if (listInfo.getName().length() == 0 || listInfo.getName().length() > 25) {
            throw new ApiRequestException("Incorrect list name length", HttpStatus.BAD_REQUEST);
        }
        Lists listFromDb = listsRepository.findById(listInfo.getId())
                .orElseThrow(() -> new ApiRequestException("List not found", HttpStatus.NOT_FOUND));
        Long userId = authenticationService.getAuthenticatedUserId();

        if (!listFromDb.getListOwner().getId().equals(userId)) {
            throw new ApiRequestException("List owner not found", HttpStatus.NOT_FOUND);
        }
        listFromDb.setName(listInfo.getName());
        listFromDb.setDescription(listInfo.getDescription());
        listFromDb.setWallpaper(listInfo.getWallpaper());
        listFromDb.setPrivate(listInfo.isPrivate());
        return listsRepository.getListById(listFromDb.getId(), userId).get();
    }

    @Override
    public String deleteList(Long listId) {  // TODO add tests
        Long userId = authenticationService.getAuthenticatedUserId();
        Lists list = listsRepository.findById(listId)
                .orElseThrow(() -> new ApiRequestException("List not found", HttpStatus.NOT_FOUND));

        if (!list.getListOwner().getId().equals(userId)) {
            throw new ApiRequestException("List owner not found", HttpStatus.BAD_REQUEST);
        }
        list.getTweets().removeAll(list.getTweets());
        list.getMembers().removeAll(list.getMembers());
        list.getFollowers().removeAll(list.getFollowers());

        if (list.getWallpaper() != null) {
            imageRepository.delete(list.getWallpaper());
        }
        listsRepository.findByListOwner_Id(userId).remove(list);
        listsRepository.delete(list);
        return "List id:" + list.getId() + " deleted.";
    }

    @Override
    @Transactional
    public Boolean followList(Long listId) {
        User user = authenticationService.getAuthenticatedUser();
        Lists list = listsRepository.findByIdAndIsPrivateFalse(listId)
                .orElseThrow(() -> new ApiRequestException("List not found", HttpStatus.NOT_FOUND));
        // TODO if user blocked by other user, can the user follow list???
        Optional<User> listFollower = list.getFollowers().stream()
                .filter(follower -> follower.getId().equals(user.getId()))
                .findFirst();
        List<User> listFollowers = list.getFollowers();

        if (listFollower.isPresent()) {
            listFollowers.remove(listFollower.get());
            if (list.getPinnedDate() != null) {
                list.setPinnedDate(null);
            }
            user.getUserLists().remove(list);
            return false;
        } else {
            listFollowers.add(user);
            user.getUserLists().add(list);
            return true;
        }
    }

    @Override
    @Transactional
    public Boolean pinList(Long listId) {
        Long userId = authenticationService.getAuthenticatedUserId();
        List<Lists> userLists = listsRepository.findByListOwner_Id(userId);
        Optional<Lists> list = userLists.stream()
                .filter(userList -> userList.getId().equals(listId))
                .findFirst();

        if (list.isPresent()) {
            if (list.get().getPinnedDate() == null) {
                list.get().setPinnedDate(LocalDateTime.now().withNano(0));
                return true;
            } else {
                list.get().setPinnedDate(null);
                return false;
            }
        } else {
            throw new ApiRequestException("List not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public List<Long> addUserToLists(Long userId, List<Lists> lists) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        checkUserIsBlocked(authUserId, userId);
        User user = userRepository.getValidUser(userId, authUserId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        checkUserIsBlocked(user.getId(), authUserId);
        List<Lists> userLists = listsRepository.findByListOwner_Id(authUserId);
        Set<Lists> commonLists = userLists.stream()
                .distinct()
                .filter(lists::contains)
                .collect(Collectors.toSet());
        commonLists.forEach((list) -> {
            Optional<User> userInList = list.getMembers().stream()
                    .filter(member -> member.getId().equals(user.getId()))
                    .findFirst();

            userLists.forEach((userList) -> {
                Optional<User> memberInUserList = userList.getMembers().stream()
                        .filter(member -> member.getId().equals(user.getId()))
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
        return userLists.stream()
                .map(Lists::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Boolean addUserToList(Long userId, Long listId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        checkUserIsBlocked(authUserId, userId);
        User user = userRepository.getValidUser(userId, authUserId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        checkUserIsBlocked(user.getId(), authUserId);
        Lists list = listsRepository.findById(listId)
                .orElseThrow(() -> new ApiRequestException("List not found", HttpStatus.NOT_FOUND));
        Optional<User> listMember = list.getMembers().stream()
                .filter(member -> member.getId().equals(user.getId()))
                .findFirst();

        if (listMember.isPresent()) {
            list.getMembers().remove(user);
            return false;
        } else {
            list.getMembers().add(user);
            return true;
        }
    }

    @Override
    public List<TweetProjection> getTweetsByListId(Long listId) {
        List<TweetsProjection> tweets = listsRepository.getTweetsByListId(listId);
        return tweets.contains(null) ? new ArrayList<>() : tweets.stream()
                .map(TweetsProjection::getTweet)
                .collect(Collectors.toList());
    }

    @Override
    public BaseListProjection getListDetails(Long listId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return listsRepository.getListDetails(listId, authUserId)
                .orElseThrow(() -> new ApiRequestException("List not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Map<String, Object> getListMembers(Long listId, Long listOwnerId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();

        if (!listOwnerId.equals(authUserId)) {
            List<ListsMemberProjection> listMembers = listsRepository.getListMembers(listId, ListsMemberProjection.class);
            return Map.of("userMembers", listMembers);
        } else {
            List<ListsOwnerMemberProjection> listMembers = listsRepository.getListMembers(listId, ListsOwnerMemberProjection.class);
            return Map.of("authUserMembers", listMembers);
        }
    }

    @Override
    public List<Map<String, Object>> searchListMembersByUsername(Long listId, String username) {
        List<Map<String, Object>> members = new ArrayList<>();
        listsRepository.searchListMembersByUsername(username)
                .forEach(member ->
                        members.add(Map.of(
                                "member", member.getMember(),
                                "isMemberInList", isListIncludeUser(listId, member.getMember().getId()))
                        ));
        return members;
    }

    public boolean isMyProfileFollowList(Long listId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return listsRepository.isMyProfileFollowList(listId, authUserId);
    }

    public boolean isListIncludeUser(Long listId, Long memberId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return listsRepository.isListIncludeUser(listId, authUserId, memberId);
    }

    private void checkUserIsBlocked(Long userId, Long supposedBlockedUserId) {
        boolean isPresent = userRepository.isUserBlocked(userId, supposedBlockedUserId);

        if (isPresent) {
            throw new ApiRequestException("User with ID:" + supposedBlockedUserId + " is blocked", HttpStatus.BAD_REQUEST);
        }
    }
}
