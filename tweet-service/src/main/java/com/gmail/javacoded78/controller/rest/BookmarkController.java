package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID_BOOKMARKED;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.USER_BOOKMARKS;
import static com.gmail.javacoded78.constants.PathConstants.USER_BOOKMARKS_TWEET_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_TWEETS)
public class BookmarkController {

    private final BookmarkMapper bookmarkMapper;

    @GetMapping(USER_BOOKMARKS)
    public ResponseEntity<List<TweetResponse>> getUserBookmarks(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = bookmarkMapper.getUserBookmarks(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(USER_BOOKMARKS_TWEET_ID)
    public ResponseEntity<Boolean> processUserBookmarks(@PathVariable Long tweetId) {
        return ResponseEntity.ok(bookmarkMapper.processUserBookmarks(tweetId));
    }

    @GetMapping(TWEET_ID_BOOKMARKED)
    public ResponseEntity<Boolean> getIsTweetBookmarked(@PathVariable("tweetId") Long tweetId) {
        return ResponseEntity.ok(bookmarkMapper.getIsTweetBookmarked(tweetId));
    }
}
