package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.dto.TweetAdditionalInfoUserResponse;
import com.gmail.javacoded78.enums.ReplyType;
import org.springframework.beans.factory.annotation.Value;

public interface TweetAdditionalInfoProjection {
    String getText();
    ReplyType getReplyType();
    Long getAddressedTweetId();
    boolean isDeleted();
    Long getAuthorId();

    @Value("#{@tweetServiceImpl.getTweetAdditionalInfoUser(target.authorId)}")
    TweetAdditionalInfoUserResponse getUser();
}
