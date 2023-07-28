package com.gmail.javacoded78.projection;

public interface UserChatProjection {

    Long getId();
    String getFullName();
    String getUsername();
    String getAbout();
    ImageProjection getAvatar();
    boolean getPrivateProfile();
    boolean getMutedDirectMessages();
    boolean getIsUserBlocked();
    boolean getIsMyProfileBlocked();
    boolean getIsWaitingForApprove();
    boolean getIsFollower();
    boolean getIsUserChatParticipant();
}
