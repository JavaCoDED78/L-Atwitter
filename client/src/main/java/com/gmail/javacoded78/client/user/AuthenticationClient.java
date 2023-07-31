package com.gmail.javacoded78.client.user;

import com.gmail.javacoded78.common.configuration.FeignConfiguration;
import com.gmail.javacoded78.common.dto.UserPrincipalResponse;
import com.gmail.javacoded78.common.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.gmail.javacoded78.common.controller.PathConstants.API_V1_AUTH;

@FeignClient(name = "user-service", contextId = "AuthenticationClient", configuration = FeignConfiguration.class)
public interface AuthenticationClient {

    @GetMapping(API_V1_AUTH + "/user/{email}")
    UserPrincipalResponse getUserPrincipalByEmail(@PathVariable("email") String email);

    @GetMapping(API_V1_AUTH + "/user/id")
    Long getAuthenticatedUserId();

    @GetMapping(API_V1_AUTH + "/user")
    User getAuthenticatedUser();

    @GetMapping(API_V1_AUTH + "/users")
    User getAuthUser();
}
