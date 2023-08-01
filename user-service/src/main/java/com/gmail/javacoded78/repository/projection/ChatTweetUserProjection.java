package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.common.projection.ImageProjection;

public interface ChatTweetUserProjection {

    Long getId();
    String getFullName();
    String getUsername();
    ImageProjection getAvatar();
}
