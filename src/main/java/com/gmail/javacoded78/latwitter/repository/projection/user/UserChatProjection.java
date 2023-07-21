package com.gmail.javacoded78.latwitter.repository.projection.user;

import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

public interface UserChatProjection {

    Long getId();

    String getFullName();

    String getUsername();

    String getAbout();

    ImageProjection getAvatar();

    boolean getPrivateProfile();

    boolean getMutedDirectMessages();

    @Value("#{@userServiceImpl.isUserBlockedByMyProfile(target.id)}")
    boolean getIsUserBlocked();

    @Value("#{@userServiceImpl.isMyProfileBlockedByUser(target.id)}")
    boolean getIsMyProfileBlocked();

    @Value("#{@userServiceImpl.isMyProfileWaitingForApprove(target.id)}")
    boolean getIsWaitingForApprove();

    @Value("#{@userServiceImpl.isUserFollowByOtherUser(target.id)}")
    boolean getIsFollower();

    @Value("#{@userServiceImpl.isUserChatParticipant(target.id)}")
    boolean getIsUserChatParticipant();
}
