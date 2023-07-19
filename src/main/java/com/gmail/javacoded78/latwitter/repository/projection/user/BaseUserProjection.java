package com.gmail.javacoded78.latwitter.repository.projection.user;

import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public interface BaseUserProjection {

    Long getId();

    String getFullName();

    String getUsername();

    String getAbout();

    boolean getIsPrivateProfile();

    @Value("#{T(com.gmail.javacoded78.latwitter.repository.projection.user.BaseUserProjection).convertToAvatar(target.img_id, target.img_src)}")
    Map<String, Object> getAvatar();

    @Value("#{@userServiceImpl.isUserMutedByMyProfile(target.id)}")
    boolean getIsUserMuted();

    @Value("#{@userServiceImpl.isUserBlockedByMyProfile(target.id)}")
    boolean getIsUserBlocked();

    @Value("#{@userServiceImpl.isMyProfileBlockedByUser(target.id)}")
    boolean getIsMyProfileBlocked();

    @Value("#{@userServiceImpl.isMyProfileWaitingForApprove(target.id)}")
    boolean getIsWaitingForApprove();

    @Value("#{@userServiceImpl.isUserFollowByOtherUser(target.id)}")
    boolean getIsFollower();

    @Value("#{@userServiceImpl.isMyProfileSubscribed(target.id)}")
    boolean getIsSubscriber();

    static Map<String, Object> convertToAvatar(Long id, String src) {
        return Map.of("id", id, "src", src);
    }
}
