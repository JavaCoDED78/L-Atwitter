package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

public interface TweetAuthorProjection {
    Long getId();
    String getEmail();
    String getFullName();
    String getUsername();
    ImageProjection getAvatar();

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
}
