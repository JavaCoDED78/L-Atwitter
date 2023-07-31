package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.TweetResponse;
import com.gmail.javacoded78.dto.lists.UserIdsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.gmail.javacoded78.controller.PathConstants.API_V1_TWEETS;

@FeignClient(value = "tweet-service", configuration = FeignConfiguration.class)
public interface TweetClient {

    @PostMapping(API_V1_TWEETS + "/user/ids")
    HeaderResponse<TweetResponse> getTweetsByUserIds(@RequestBody UserIdsRequest request,
                                                     @SpringQueryMap Pageable pageable);
}
