package com.gmail.javacoded78.service;

import com.gmail.javacoded78.repository.projection.BaseUserProjection;
import com.gmail.javacoded78.repository.projection.BlockedUserProjection;
import com.gmail.javacoded78.repository.projection.FollowerUserProjection;
import com.gmail.javacoded78.repository.projection.MutedUserProjection;
import com.gmail.javacoded78.repository.projection.UserProjection;
import com.gmail.javacoded78.util.TestConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceTestHelper {

    private static final ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    private static final PageRequest pageable = PageRequest.of(0, 20);

    public static Page<BlockedUserProjection> createBlockedUserProjections() {
        BlockedUserProjection blockedUserProjection1 = factory.createProjection(
                BlockedUserProjection.class,
                Map.of(
                        "id", 1L,
                        "fullName", TestConstants.FULL_NAME,
                        "username", TestConstants.USERNAME,
                        "about", TestConstants.ABOUT,
                        "avatar", TestConstants.AVATAR_SRC_1,
                        "privateProfile", false,
                        "isUserBlocked", true
                ));
        BlockedUserProjection blockedUserProjection2 = factory.createProjection(
                BlockedUserProjection.class,
                Map.of(
                        "id", 2L,
                        "fullName", TestConstants.FULL_NAME,
                        "username", TestConstants.USERNAME,
                        "about", TestConstants.ABOUT,
                        "avatar", TestConstants.AVATAR_SRC_1,
                        "privateProfile", false,
                        "isUserBlocked", true
                ));
        return new PageImpl<>(Arrays.asList(blockedUserProjection1, blockedUserProjection2), pageable, 20);
    }

    public static Page<MutedUserProjection> createMutedUserProjections() {
        MutedUserProjection mutedUserProjection1 = factory.createProjection(
                MutedUserProjection.class,
                Map.of(
                        "id", 1L,
                        "fullName", TestConstants.FULL_NAME,
                        "username", TestConstants.USERNAME,
                        "about", TestConstants.ABOUT,
                        "avatar", TestConstants.AVATAR_SRC_1,
                        "privateProfile", false,
                        "isUserMuted", true
                ));
        MutedUserProjection mutedUserProjection2 = factory.createProjection(
                MutedUserProjection.class,
                Map.of(
                        "id", 2L,
                        "fullName", TestConstants.FULL_NAME,
                        "username", TestConstants.USERNAME,
                        "about", TestConstants.ABOUT,
                        "avatar", TestConstants.AVATAR_SRC_1,
                        "privateProfile", false,
                        "isUserMuted", true
                ));
        return new PageImpl<>(Arrays.asList(mutedUserProjection1, mutedUserProjection2), pageable, 20);
    }

    public static Page<UserProjection> createUserProjections() {
        UserProjection userProjection1 = factory.createProjection(
                UserProjection.class,
                new HashMap<>() {{
                    put("id", 1L);
                    put("fullName", TestConstants.FULL_NAME);
                    put("username", TestConstants.USERNAME);
                    put("about", TestConstants.ABOUT);
                    put("avatar", TestConstants.AVATAR_SRC_1);
                    put("privateProfile", false);
                    put("mutedDirectMessages", false);
                    put("isUserBlocked", false);
                    put("isMyProfileBlocked", false);
                    put("isWaitingForApprove", false);
                    put("isFollower", false);
                }});
        UserProjection userProjection2 = factory.createProjection(
                UserProjection.class,
                new HashMap<>() {{
                    put("id", 1L);
                    put("fullName", TestConstants.FULL_NAME);
                    put("username", TestConstants.USERNAME);
                    put("about", TestConstants.ABOUT);
                    put("avatar", TestConstants.AVATAR_SRC_1);
                    put("privateProfile", false);
                    put("mutedDirectMessages", false);
                    put("isUserBlocked", false);
                    put("isMyProfileBlocked", false);
                    put("isWaitingForApprove", false);
                    put("isFollower", false);
                }});
        return new PageImpl<>(Arrays.asList(userProjection1, userProjection2), pageable, 20);
    }

    public static Page<FollowerUserProjection> createFollowerUserProjections() {
        FollowerUserProjection followerUserProjection1 = factory.createProjection(
                FollowerUserProjection.class,
                new HashMap<>() {{
                    put("id", 1L);
                    put("fullName", TestConstants.FULL_NAME);
                    put("username", TestConstants.USERNAME);
                    put("about", TestConstants.ABOUT);
                    put("avatar", TestConstants.AVATAR_SRC_1);
                }});
        FollowerUserProjection followerUserProjection2 = factory.createProjection(
                FollowerUserProjection.class,
                new HashMap<>() {{
                    put("id", 1L);
                    put("fullName", TestConstants.FULL_NAME);
                    put("username", TestConstants.USERNAME);
                    put("about", TestConstants.ABOUT);
                    put("avatar", TestConstants.AVATAR_SRC_1);
                }});
        return new PageImpl<>(Arrays.asList(followerUserProjection1, followerUserProjection2), pageable, 20);
    }

    public static List<BaseUserProjection> createBaseUserProjections() {
        BaseUserProjection baseUserProjection1 = factory.createProjection(
                BaseUserProjection.class,
                new HashMap<>() {{
                    put("id", 1L);
                    put("fullName", TestConstants.FULL_NAME);
                    put("username", TestConstants.USERNAME);
                    put("about", TestConstants.ABOUT);
                    put("avatar", TestConstants.AVATAR_SRC_1);
                    put("privateProfile", false);
                    put("isUserBlocked", false);
                    put("isMyProfileBlocked", false);
                    put("isWaitingForApprove", false);
                    put("isFollower", false);
                }});
        BaseUserProjection baseUserProjection2 = factory.createProjection(
                BaseUserProjection.class,
                new HashMap<>() {{
                    put("id", 1L);
                    put("fullName", TestConstants.FULL_NAME);
                    put("username", TestConstants.USERNAME);
                    put("about", TestConstants.ABOUT);
                    put("avatar", TestConstants.AVATAR_SRC_1);
                    put("privateProfile", false);
                    put("isUserBlocked", false);
                    put("isMyProfileBlocked", false);
                    put("isWaitingForApprove", false);
                    put("isFollower", false);
                }});

        return Arrays.asList(baseUserProjection1, baseUserProjection2);
    }
}
