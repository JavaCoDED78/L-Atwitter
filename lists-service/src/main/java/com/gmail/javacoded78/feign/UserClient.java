package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.lists.ListMemberResponse;
import com.gmail.javacoded78.dto.lists.ListOwnerResponse;
import com.gmail.javacoded78.dto.lists.UserIdsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.gmail.javacoded78.controller.PathConstants.API_V1_USER;

@FeignClient(name = "user-service", contextId = "UserClient", configuration = FeignConfiguration.class)
public interface UserClient {

    @GetMapping(API_V1_USER + "/is_blocked/{userId}/{supposedBlockedUserId}")
    Boolean isUserBlocked(@PathVariable("userId") Long userId,
                          @PathVariable("supposedBlockedUserId") Long supposedBlockedUserId);

    @GetMapping(API_V1_USER + "/list/owner/{userId}")
    ListOwnerResponse getListOwnerById(@PathVariable("userId") Long userId);

    @GetMapping(API_V1_USER + "/is_private/{userId}")
    Boolean isUserHavePrivateProfile(@PathVariable("userId") Long userId);

    @PostMapping(API_V1_USER + "/list/participants")
    List<ListMemberResponse> getListParticipantsByIds(@RequestBody UserIdsRequest request);

    @GetMapping(API_V1_USER + "/list/participants/{username}")
    List<ListMemberResponse> searchListMembersByUsername(@PathVariable("username") String username);
}
