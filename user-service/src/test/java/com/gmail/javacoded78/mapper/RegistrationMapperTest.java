package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.request.RegistrationRequest;
import com.gmail.javacoded78.service.RegistrationService;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class RegistrationMapperTest extends AbstractAuthTest {

    private final RegistrationMapper registrationMapper;

    @MockBean
    private final RegistrationService registrationService;

    @MockBean
    private final AuthenticationMapper authenticationMapper;

    private final BindingResult bindingResult = mock(BindingResult.class);

    @Test
    void registration() {
        RegistrationRequest request = new RegistrationRequest();
        request.setBirthday(TestConstants.BIRTHDAY);
        request.setEmail(TestConstants.USER_EMAIL);
        request.setUsername(TestConstants.USERNAME);
        when(registrationService.registration(request, bindingResult)).thenReturn("User data checked.");
        assertEquals("User data checked.", registrationMapper.registration(request, bindingResult));
        verify(registrationService, times(1)).registration(request, bindingResult);
    }

    @Test
    void sendRegistrationCode() {
        when(registrationService.sendRegistrationCode(TestConstants.USER_EMAIL, bindingResult)).thenReturn("Registration code sent successfully");
        assertEquals("Registration code sent successfully", registrationMapper.sendRegistrationCode(TestConstants.USER_EMAIL, bindingResult));
        verify(registrationService, times(1)).sendRegistrationCode(TestConstants.USER_EMAIL, bindingResult);
    }
}
