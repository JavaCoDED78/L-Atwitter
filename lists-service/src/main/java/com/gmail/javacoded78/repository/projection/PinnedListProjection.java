package com.gmail.javacoded78.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface PinnedListProjection {

    Long getId();
    String getName();
    String getAltWallpaper();
    String getWallpaper();
    boolean getIsPrivate();

    @Value("#{@listsServiceImpl.isListPinned(target.id)}")
    boolean getIsListPinned();
}
