package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.request.SettingsRequest;
import com.gmail.javacoded78.dto.response.UserPhoneResponse;
import com.gmail.javacoded78.service.UserSettingsService;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class UserSettingsMapperTest extends AbstractAuthTest {

    private final UserSettingsMapper userSettingsMapper;

    @MockBean
    private final AuthenticationMapper authenticationMapper;

    @MockBean
    private final UserSettingsService userSettingsService;

    @Test
     void updateUsername() {
        SettingsRequest request = new SettingsRequest();
        request.setUsername(TestConstants.USERNAME);
        when(userSettingsService.updateUsername(TestConstants.USERNAME)).thenReturn(TestConstants.USERNAME);
        assertEquals(TestConstants.USERNAME, userSettingsMapper.updateUsername(request));
        verify(userSettingsService, times(1)).updateUsername(TestConstants.USERNAME);
    }

    @Test
    void updatePhone() {
        SettingsRequest request = new SettingsRequest();
        request.setCountryCode(TestConstants.COUNTRY_CODE);
        request.setPhone(TestConstants.PHONE);
        Map<String, Object> phoneParams = new HashMap<>();
        phoneParams.put("countryCode", TestConstants.COUNTRY_CODE);
        phoneParams.put("phone", TestConstants.PHONE);
        UserPhoneResponse userPhoneResponse = new UserPhoneResponse(TestConstants.COUNTRY_CODE, TestConstants.PHONE);
        when(userSettingsService.updatePhone(TestConstants.COUNTRY_CODE, TestConstants.PHONE)).thenReturn(phoneParams);
        assertEquals(userPhoneResponse, userSettingsMapper.updatePhone(request));
        verify(userSettingsService, times(1)).updatePhone(TestConstants.COUNTRY_CODE, TestConstants.PHONE);
    }

    @Test
    void updateCountry() {
        SettingsRequest request = new SettingsRequest();
        request.setCountryCode(TestConstants.COUNTRY);
        when(userSettingsService.updateCountry(TestConstants.COUNTRY)).thenReturn(TestConstants.COUNTRY);
        assertEquals(TestConstants.COUNTRY, userSettingsMapper.updateCountry(request));
        verify(userSettingsService, times(1)).updateCountry(TestConstants.COUNTRY);

    }
}
