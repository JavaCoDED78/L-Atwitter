package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.amqp.AmqpProducer;
import com.gmail.javacoded78.dto.request.AuthenticationRequest;
import com.gmail.javacoded78.dto.request.EmailRequest;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.exception.InputFieldException;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projection.AuthUserProjection;
import com.gmail.javacoded78.repository.projection.UserCommonProjection;
import com.gmail.javacoded78.repository.projection.UserPrincipalProjection;
import com.gmail.javacoded78.security.JwtProvider;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.UserServiceTestHelper;
import com.gmail.javacoded78.service.util.UserServiceHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_PASSWORD;
import static com.gmail.javacoded78.constants.ErrorMessage.INVALID_PASSWORD_RESET_CODE;
import static com.gmail.javacoded78.constants.ErrorMessage.PASSWORDS_NOT_MATCH;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class AuthenticationServiceImplTest extends AbstractAuthTest {

    private final AuthenticationService authenticationService;

    @MockBean
    private final UserRepository userRepository;

    @MockBean
    private final UserServiceHelper userServiceHelper;

    @MockBean
    private final PasswordEncoder passwordEncoder;

    @MockBean
    private final JwtProvider jwtProvider;

    @MockBean
    private final AmqpProducer amqpProducer;

    private final BindingResult bindingResult = mock(BindingResult.class);
    private final UserCommonProjection userCommonProjection = UserServiceTestHelper.createUserCommonProjection();
    private final AuthUserProjection authUserProjection = UserServiceTestHelper.createAuthUserProjection();

    @Test
    void getAuthenticatedUser_ShouldReturnAuthenticatedUser() {
        User user = new User();
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        assertEquals(user, authenticationService.getAuthenticatedUser());
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    void getAuthenticatedUser_ShouldThrowUserNotFoundException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                authenticationService::getAuthenticatedUser);
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void getUserPrincipalByEmail_ShouldReturnAuthenticatedUser() {
        UserPrincipalProjection userPrincipalProjection = UserServiceTestHelper.createUserPrincipalProjection();
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, UserPrincipalProjection.class))
                .thenReturn(Optional.of(userPrincipalProjection));
        assertEquals(userPrincipalProjection, authenticationService.getUserPrincipalByEmail(TestConstants.USER_EMAIL));
        verify(userRepository, times(1)).getUserByEmail(TestConstants.USER_EMAIL, UserPrincipalProjection.class);
    }

    @Test
    void getUserPrincipalByEmail_ShouldThrowUserNotFoundException() {
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, UserPrincipalProjection.class))
                .thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> authenticationService.getUserPrincipalByEmail(TestConstants.USER_EMAIL));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void login_ShouldReturnAuthenticatedUser() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(TestConstants.USER_EMAIL);
        Map<String, Object> userMap = Map.of("user", authUserProjection, "token", TestConstants.AUTH_TOKEN);
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, AuthUserProjection.class))
                .thenReturn(Optional.of(authUserProjection));
        when(jwtProvider.createToken(TestConstants.USER_EMAIL, "USER")).thenReturn(TestConstants.AUTH_TOKEN);
        assertEquals(userMap, authenticationService.login(request, bindingResult));
        verify(userServiceHelper, times(1)).processInputErrors(bindingResult);
        verify(userRepository, times(1)).getUserByEmail(TestConstants.USER_EMAIL, AuthUserProjection.class);
        verify(jwtProvider, times(1)).createToken(TestConstants.USER_EMAIL, "USER");
    }

    @Test
    void login_ShouldThrowUserNotFoundException() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(TestConstants.USER_EMAIL);
        BindingResult bindingResult = mock(BindingResult.class);
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, AuthUserProjection.class))
                .thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> authenticationService.login(request, bindingResult));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getUserByToken_ShouldReturnAuthUserProjection() {
        Map<String, Object> userMap = Map.of("user", authUserProjection, "token", TestConstants.AUTH_TOKEN);
        when(userRepository.getUserById(any(), AuthUserProjection.class))
                .thenReturn(Optional.of(authUserProjection));
        when(jwtProvider.createToken(TestConstants.USER_EMAIL, "USER")).thenReturn(TestConstants.AUTH_TOKEN);
        assertEquals(userMap, authenticationService.getUserByToken());
        verify(userRepository, times(1)).getUserById(any(), AuthUserProjection.class);
        verify(jwtProvider, times(1)).createToken(TestConstants.USER_EMAIL, "USER");
    }

    @Test
    void getUserByToken_ShouldThrowUserNotFoundException() {
        when(userRepository.getUserById(any(), AuthUserProjection.class))
                .thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                authenticationService::getUserByToken);
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getExistingEmail_ShouldReturnSuccessMessage() {
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, UserCommonProjection.class))
                .thenReturn(Optional.of(userCommonProjection));
        assertEquals("Reset password code is send to your E-mail",
                authenticationService.getExistingEmail(TestConstants.USER_EMAIL, bindingResult));
        verify(userServiceHelper, times(1)).processInputErrors(bindingResult);
        verify(userRepository, times(1)).getUserByEmail(TestConstants.USER_EMAIL, UserCommonProjection.class);
    }

    @Test
    void getExistingEmail_ShouldThrowEmailNotFoundException() {
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, UserCommonProjection.class))
                .thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> authenticationService.getExistingEmail(TestConstants.USER_EMAIL, bindingResult));
        assertEquals(EMAIL_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void sendPasswordResetCode_ShouldReturnSuccessMessage() {
        EmailRequest request = EmailRequest.builder()
                .to(userCommonProjection.getEmail())
                .subject("Password reset")
                .template("password-reset-template")
                .attributes(Map.of(
                        "fullName", userCommonProjection.getFullName(),
                        "passwordResetCode", TestConstants.PASSWORD_RESET_CODE))
                .build();
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, UserCommonProjection.class))
                .thenReturn(Optional.of(userCommonProjection));
        when(userRepository.getPasswordResetCode(TestConstants.USER_ID)).thenReturn(TestConstants.PASSWORD_RESET_CODE);
        assertEquals("Reset password code is send to your E-mail",
                authenticationService.sendPasswordResetCode(TestConstants.USER_EMAIL, bindingResult));
        verify(userServiceHelper, times(1)).processInputErrors(bindingResult);
        verify(userRepository, times(1)).getUserByEmail(TestConstants.USER_EMAIL, UserCommonProjection.class);
        verify(userRepository, times(1)).getPasswordResetCode(TestConstants.USER_ID);
        verify(amqpProducer, times(1)).sendEmail(request);
    }

    @Test
    void sendPasswordResetCode_ShouldThrowEmailNotFoundException() {
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, UserCommonProjection.class))
                .thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> authenticationService.sendPasswordResetCode(TestConstants.USER_EMAIL, bindingResult));
        assertEquals(EMAIL_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getUserByPasswordResetCode_ShouldReturnAuthUserProjection() {
        when(userRepository.getByPasswordResetCode(TestConstants.PASSWORD_RESET_CODE))
                .thenReturn(Optional.of(authUserProjection));
        assertEquals(authUserProjection, authenticationService.getUserByPasswordResetCode(TestConstants.PASSWORD_RESET_CODE));
        verify(userRepository, times(1)).getByPasswordResetCode(TestConstants.PASSWORD_RESET_CODE);
    }

    @Test
    void getUserByPasswordResetCode_ShouldThrowEmailNotFoundException() {
        when(userRepository.getByPasswordResetCode(TestConstants.PASSWORD_RESET_CODE))
                .thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> authenticationService.getUserByPasswordResetCode(TestConstants.PASSWORD_RESET_CODE));
        assertEquals(INVALID_PASSWORD_RESET_CODE, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void passwordReset_ShouldReturnSuccessMessage() {
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, UserCommonProjection.class))
                .thenReturn(Optional.of(userCommonProjection));
        when(passwordEncoder.encode(TestConstants.PASSWORD)).thenReturn(TestConstants.PASSWORD);
        assertEquals("Password successfully changed!", authenticationService.passwordReset(
                TestConstants.USER_EMAIL, TestConstants.PASSWORD, "test", bindingResult));
        verify(userRepository, times(1)).getUserByEmail(TestConstants.USER_EMAIL, UserCommonProjection.class);
        verify(userRepository, times(1)).updatePassword(TestConstants.PASSWORD, userCommonProjection.getId());
        verify(userRepository, times(1)).updatePasswordResetCode(null, userCommonProjection.getId());
    }

    @Test
    void passwordReset_ShouldThrowInputFieldException() {
        InputFieldException exception = assertThrows(InputFieldException.class,
                () -> authenticationService.passwordReset(TestConstants.USER_EMAIL, null, "test", bindingResult));
        assertEquals(Map.of("password", PASSWORDS_NOT_MATCH), exception.getErrorsMap());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void passwordReset_ShouldThrowUserNotFoundInputFieldException() {
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, UserCommonProjection.class))
                .thenReturn(Optional.empty());
        InputFieldException exception = assertThrows(InputFieldException.class,
                () -> authenticationService.passwordReset(TestConstants.USER_EMAIL, TestConstants.PASSWORD, "test", bindingResult));
        assertEquals(Map.of("email", EMAIL_NOT_FOUND), exception.getErrorsMap());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void currentPasswordReset_ShouldReturnSuccessMessage() {
        when(userRepository.getUserPasswordById(TestConstants.USER_ID)).thenReturn(TestConstants.PASSWORD);
        when(passwordEncoder.matches(TestConstants.PASSWORD, TestConstants.PASSWORD)).thenReturn(true);
        when(passwordEncoder.encode(TestConstants.PASSWORD)).thenReturn(TestConstants.PASSWORD);
        assertEquals("Your password has been successfully updated.", authenticationService.currentPasswordReset(
                TestConstants.PASSWORD, TestConstants.PASSWORD, "test", bindingResult));
        verify(userRepository, times(1)).getUserPasswordById(TestConstants.USER_ID);
        verify(passwordEncoder, times(1)).matches(TestConstants.PASSWORD, TestConstants.PASSWORD);
        verify(userRepository, times(1)).updatePassword(TestConstants.PASSWORD, TestConstants.USER_ID);
        verify(passwordEncoder, times(1)).encode(TestConstants.PASSWORD);
    }

    @Test
    public void currentPasswordReset_ShouldThrowEmailNotFoundException() {
        when(userRepository.getUserPasswordById(TestConstants.USER_ID)).thenReturn("test");
        when(passwordEncoder.matches(TestConstants.PASSWORD, "test")).thenReturn(false);
        InputFieldException exception = assertThrows(InputFieldException.class,
                () -> authenticationService.currentPasswordReset(TestConstants.PASSWORD, TestConstants.PASSWORD, "test", bindingResult));
        assertEquals(Map.of("currentPassword", INCORRECT_PASSWORD), exception.getErrorsMap());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void currentPasswordReset_ShouldThrowUserNotFoundInputFieldException() {
        when(userRepository.getUserPasswordById(TestConstants.USER_ID)).thenReturn("test");
        when(passwordEncoder.matches(TestConstants.PASSWORD, TestConstants.PASSWORD)).thenReturn(true);
        InputFieldException exception = assertThrows(InputFieldException.class,
                () -> authenticationService.currentPasswordReset(TestConstants.PASSWORD, null, "test", bindingResult));
        assertEquals(Map.of("password", PASSWORDS_NOT_MATCH), exception.getErrorsMap());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
