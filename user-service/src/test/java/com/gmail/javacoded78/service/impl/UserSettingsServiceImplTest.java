package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.UserSettingsRepository;
import com.gmail.javacoded78.repository.projection.AuthUserProjection;
import com.gmail.javacoded78.security.JwtProvider;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.UserServiceTestHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_HAS_ALREADY_BEEN_TAKEN;
import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_USERNAME_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.INVALID_PHONE_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class UserSettingsServiceImplTest extends AbstractAuthTest {

    private final UserSettingsServiceImpl userSettingsService;

    @MockBean
    private final AuthenticationService authenticationService;

    @MockBean
    private final UserRepository userRepository;

    @MockBean
    private final UserSettingsRepository userSettingsRepository;

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    void updateUsername_ShouldReturnUpdatedUsername() {
        when(authenticationService.getAuthenticatedUserId()).thenReturn(TestConstants.USER_ID);
        assertEquals(TestConstants.USERNAME, userSettingsService.updateUsername(TestConstants.USERNAME));
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(userSettingsRepository, times(1)).updateUsername(TestConstants.USERNAME, TestConstants.USER_ID);
    }

    @Test
    void updateUsername_ShouldThrowUsernameLengthException() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> userSettingsService.updateUsername(""));
        assertEquals(INCORRECT_USERNAME_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void updateEmail_ShouldReturnUpdatedUser() {
        AuthUserProjection authUserProjection = UserServiceTestHelper.createAuthUserProjection();
        when(userSettingsRepository.isEmailExist(TestConstants.USER_EMAIL)).thenReturn(true);
        when(authenticationService.getAuthenticatedUserId()).thenReturn(TestConstants.USER_ID);
        when(jwtProvider.createToken(TestConstants.USER_EMAIL, "USER")).thenReturn(TestConstants.AUTH_TOKEN);
        when(userRepository.getUserById(TestConstants.USER_ID, AuthUserProjection.class)).thenReturn(Optional.of(authUserProjection));
        assertEquals(Map.of("user", authUserProjection, "token", TestConstants.AUTH_TOKEN),
                userSettingsService.updateEmail(TestConstants.USER_EMAIL));
        verify(userSettingsRepository, times(1)).isEmailExist(TestConstants.USER_EMAIL);
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(userSettingsRepository, times(1)).updateEmail(TestConstants.USER_EMAIL, TestConstants.USER_ID);
        verify(jwtProvider, times(1)).createToken(TestConstants.USER_EMAIL, "USER");
        verify(userRepository, times(1)).getUserById(TestConstants.USER_ID, AuthUserProjection.class);
    }

    @Test
    void updateEmail_ShouldThrowEmailException() {
        when(userSettingsRepository.isEmailExist(TestConstants.USER_EMAIL)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> userSettingsService.updateEmail(TestConstants.USER_EMAIL));
        assertEquals(EMAIL_HAS_ALREADY_BEEN_TAKEN, exception.getMessage());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void updatePhone_ShouldReturnUpdatedPhone() {
        when(authenticationService.getAuthenticatedUserId()).thenReturn(TestConstants.USER_ID);
        assertEquals(Map.of("countryCode", TestConstants.COUNTRY_CODE, "phone", TestConstants.PHONE),
                userSettingsService.updatePhone(TestConstants.COUNTRY_CODE, TestConstants.PHONE));
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(userSettingsRepository, times(1))
                .updatePhone(TestConstants.COUNTRY_CODE, TestConstants.PHONE, TestConstants.USER_ID);
    }

    @Test
    void updatePhone_ShouldThrowInvalidPhoneNumberException() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> userSettingsService.updatePhone(TestConstants.COUNTRY_CODE, 1L));
        assertEquals(INVALID_PHONE_NUMBER, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void updateCountry_ShouldReturnUpdatedCountry() {
        when(authenticationService.getAuthenticatedUserId()).thenReturn(TestConstants.USER_ID);
        assertEquals(TestConstants.COUNTRY, userSettingsService.updateCountry(TestConstants.COUNTRY));
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(userSettingsRepository, times(1)).updateCountry(TestConstants.COUNTRY, TestConstants.USER_ID);
    }
}
