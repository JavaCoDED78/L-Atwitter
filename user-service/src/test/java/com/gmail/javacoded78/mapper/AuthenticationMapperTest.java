package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.request.AuthenticationRequest;
import com.gmail.javacoded78.dto.request.CurrentPasswordResetRequest;
import com.gmail.javacoded78.dto.request.PasswordResetRequest;
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

    BindingResult bindingResult = mock(BindingResult.class);

    @Test
    void login() {
        AuthUserProjection authUserProjection = UserServiceTestHelper.createAuthUserProjection();
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(TestConstants.USER_EMAIL);
        Map<String, Object> credentials = Map.of(
                "user", authUserProjection,
                "token", TestConstants.AUTH_TOKEN);
        when(authenticationService.login(request, bindingResult)).thenReturn(credentials);
        authenticationMapper.login(request, bindingResult);
        verify(authenticationService, times(1)).login(request, bindingResult);
    }

    @Test
    void getUserByToken() {
        AuthUserProjection authUserProjection = UserServiceTestHelper.createAuthUserProjection();
        Map<String, Object> credentials = Map.of(
                "user", authUserProjection,
                "token", TestConstants.AUTH_TOKEN);
        when(authenticationService.getUserByToken()).thenReturn(credentials);
        authenticationMapper.getUserByToken();
        verify(authenticationService, times(1)).getUserByToken();
    }

    @Test
    void getExistingEmail() {
        when(authenticationService.getExistingEmail(TestConstants.USER_EMAIL, bindingResult))
                .thenReturn("Reset password code is send to your E-mail");
        authenticationMapper.getExistingEmail(TestConstants.USER_EMAIL, bindingResult);
        verify(authenticationService, times(1)).getExistingEmail(TestConstants.USER_EMAIL, bindingResult);
    }

    @Test
    void sendPasswordResetCode() {
        when(authenticationService.sendPasswordResetCode(TestConstants.USER_EMAIL, bindingResult))
                .thenReturn("Reset password code is send to your E-mail");
        authenticationMapper.sendPasswordResetCode(TestConstants.USER_EMAIL, bindingResult);
        verify(authenticationService, times(1)).sendPasswordResetCode(TestConstants.USER_EMAIL, bindingResult);
    }

    @Test
    void getUserByPasswordResetCode() {
        AuthUserProjection authUserProjection = UserServiceTestHelper.createAuthUserProjection();
        when(authenticationService.getUserByPasswordResetCode(TestConstants.ACTIVATION_CODE)).thenReturn(authUserProjection);
        authenticationMapper.getUserByPasswordResetCode(TestConstants.ACTIVATION_CODE);
        verify(authenticationService, times(1)).getUserByPasswordResetCode(TestConstants.ACTIVATION_CODE);
    }

    @Test
    void passwordReset() {
        PasswordResetRequest request = new PasswordResetRequest();
        request.setEmail(TestConstants.USER_EMAIL);
        request.setPassword(TestConstants.PASSWORD);
        request.setPassword2(TestConstants.PASSWORD);
        when(authenticationService.passwordReset(
                TestConstants.USER_EMAIL,
                TestConstants.PASSWORD,
                TestConstants.PASSWORD,
                bindingResult
        )).thenReturn("Password successfully changed!");
        authenticationMapper.passwordReset(request, bindingResult);
        verify(authenticationService, times(1)).passwordReset(
                TestConstants.USER_EMAIL,
                TestConstants.PASSWORD,
                TestConstants.PASSWORD,
                bindingResult
        );
    }

    @Test
    void currentPasswordReset() {
        CurrentPasswordResetRequest request = new CurrentPasswordResetRequest();
        request.setCurrentPassword(TestConstants.PASSWORD);
        request.setPassword(TestConstants.PASSWORD);
        request.setPassword2(TestConstants.PASSWORD);
        when(authenticationService.currentPasswordReset(
                TestConstants.PASSWORD,
                TestConstants.PASSWORD,
                TestConstants.PASSWORD,
                bindingResult
        )).thenReturn("Your password has been successfully updated.");
        authenticationMapper.currentPasswordReset(request, bindingResult);
        verify(authenticationService, times(1)).currentPasswordReset(
                TestConstants.PASSWORD,
                TestConstants.PASSWORD,
                TestConstants.PASSWORD,
                bindingResult
        );
    }
}
