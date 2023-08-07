package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.chat.ChatUserParticipantResponse;
import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.response.UserChatResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.javacoded78.constants.FeignConstants.USER_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_USER;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_PARTICIPANT_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_VALID_IDS;
import static com.gmail.javacoded78.constants.PathConstants.IS_EXISTS_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_MY_PROFILE_BLOCKED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_USER_BLOCKED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_USERNAME;
import static com.gmail.javacoded78.constants.PathConstants.USER_ID;

@FeignClient(name = USER_SERVICE, path = API_V1_USER, contextId = "UserClient", configuration = FeignConfiguration.class)
public interface UserClient {

    @CircuitBreaker(name = USER_SERVICE)
    @GetMapping(CHAT_PARTICIPANT_USER_ID)
    ChatUserParticipantResponse getChatParticipant(@PathVariable("userId") Long userId);

    @CircuitBreaker(name = USER_SERVICE)
    @GetMapping(IS_USER_BLOCKED_USER_ID)
    Boolean isUserBlockedByMyProfile(@PathVariable("userId") Long userId);

    @CircuitBreaker(name = USER_SERVICE)
    @GetMapping(IS_MY_PROFILE_BLOCKED_USER_ID)
    Boolean isMyProfileBlockedByUser(@PathVariable("userId") Long userId);

    @CircuitBreaker(name = USER_SERVICE)
    @GetMapping(IS_EXISTS_USER_ID)
    Boolean isUserExists(@PathVariable("userId") Long userId);

    @CircuitBreaker(name = USER_SERVICE)
    @GetMapping(USER_ID)
    UserResponse getUserResponseById(@PathVariable("userId") Long userId);

    @CircuitBreaker(name = USER_SERVICE, fallbackMethod = "defaultEmptyUserChatResponse")
    @GetMapping(SEARCH_USERNAME)
    HeaderResponse<UserChatResponse> searchUsersByUsername(@PathVariable("username") String username,
                                                           @SpringQueryMap Pageable pageable);

    @CircuitBreaker(name = USER_SERVICE, fallbackMethod = "defaultEmptyIdsList")
    @PostMapping(CHAT_VALID_IDS)
    List<Long> validateChatUsersIds(@RequestBody IdsRequest request);

    default HeaderResponse<UserChatResponse> defaultEmptyUserChatResponse(Throwable throwable) {
        return new HeaderResponse<>(new ArrayList<>(), HttpHeaders.EMPTY);
    }

    default ArrayList<Long> defaultEmptyIdsList(Throwable throwable) {
        return new ArrayList<>();
    }
}
