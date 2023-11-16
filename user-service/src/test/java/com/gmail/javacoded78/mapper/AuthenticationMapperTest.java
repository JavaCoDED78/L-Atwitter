package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.request.AuthenticationRequest;
import com.gmail.javacoded78.repository.projection.AuthUserProjection;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.UserServiceTestHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class AuthenticationMapperTest extends AbstractAuthTest {

    private final AuthenticationMapper authenticationMapper;

    @MockBean
    private final AuthenticationService authenticationService;

    @Test
    void login() {
        AuthUserProjection authUserProjection = UserServiceTestHelper.createAuthUserProjection();
        BindingResult bindingResult = mock(BindingResult.class);
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(TestConstants.USER_EMAIL);
        Map<String, Object> credentials = Map.of(
                "user", authUserProjection,
                "token", TestConstants.AUTH_TOKEN);
        when(authenticationService.login(request, bindingResult)).thenReturn(credentials);
        authenticationMapper.login(request, bindingResult);
        verify(authenticationService, times(1)).login(request, bindingResult);
    }
}
