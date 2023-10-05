package com.gmail.javacoded78.integration.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.SettingsRequest;
import com.gmail.javacoded78.enums.BackgroundColorType;
import com.gmail.javacoded78.enums.ColorSchemeType;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_HAS_ALREADY_BEEN_TAKEN;
import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_USERNAME_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.INVALID_GENDER_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.INVALID_PHONE_NUMBER;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.DIRECT;
import static com.gmail.javacoded78.constants.PathConstants.EMAIL;
import static com.gmail.javacoded78.constants.PathConstants.LANGUAGE;
import static com.gmail.javacoded78.constants.PathConstants.PRIVATE;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_USER_SETTINGS_UPDATE;
import static com.gmail.javacoded78.util.TestConstants.BACKGROUND_COLOR;
import static com.gmail.javacoded78.util.TestConstants.COLOR_SCHEME;
import static com.gmail.javacoded78.util.TestConstants.COUNTRY;
import static com.gmail.javacoded78.util.TestConstants.GENDER;
import static com.gmail.javacoded78.util.TestConstants.PHONE;
import static com.gmail.javacoded78.util.TestConstants.USERNAME;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserSettingsControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Test
    @DisplayName("[200] PUT /ui/v1/settings/update/username - Update username")
    void updateUsername() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setUsername("test");
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + USERNAME)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("test")));
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/settings/update/username - Should username length is 0")
    void updateUsername_ShouldUsernameLengthIs0() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setUsername("");
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + USERNAME)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_USERNAME_LENGTH)));
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/settings/update/username - Should username length more than 50")
    void updateUsername_ShouldUsernameLengthMoreThan50() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setUsername(TestConstants.LINK_DESCRIPTION);
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + USERNAME)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_USERNAME_LENGTH)));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/settings/update/email - Update email")
    void updateEmail() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setEmail("test2013@test.test");
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + EMAIL)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.user.id").value(TestConstants.USER_ID),
                        jsonPath("$.user.email").value("test2013@test.test"),
                        jsonPath("$.user.fullName").value(TestConstants.FULL_NAME),
                        jsonPath("$.user.username").value(TestConstants.USERNAME),
                        jsonPath("$.user.location").value(TestConstants.LOCATION),
                        jsonPath("$.user.about").value(TestConstants.ABOUT),
                        jsonPath("$.user.website").value(TestConstants.WEBSITE),
                        jsonPath("$.user.countryCode").value(TestConstants.COUNTRY),
                        jsonPath("$.user.phone").value(TestConstants.PHONE),
                        jsonPath("$.user.country").value(TestConstants.COUNTRY),
                        jsonPath("$.user.gender").value(TestConstants.GENDER),
                        jsonPath("$.user.birthday").value(TestConstants.BIRTHDAY),
                        jsonPath("$.user.registrationDate").value(TestConstants.REGISTRATION_DATE),
                        jsonPath("$.user.tweetCount").value(TestConstants.TWEET_COUNT),
                        jsonPath("$.user.mediaTweetCount").value(TestConstants.MEDIA_TWEET_COUNT),
                        jsonPath("$.user.likeCount").value(TestConstants.LIKE_TWEET_COUNT),
                        jsonPath("$.user.notificationsCount").value(3),
                        jsonPath("$.user.active").value(true),
                        jsonPath("$.user.profileCustomized").value(true),
                        jsonPath("$.user.profileStarted").value(true),
                        jsonPath("$.user.backgroundColor").value(TestConstants.BACKGROUND_COLOR),
                        jsonPath("$.user.colorScheme").value(TestConstants.COLOR_SCHEME),
                        jsonPath("$.user.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.user.wallpaper").value(TestConstants.WALLPAPER_SRC),
                        jsonPath("$.user.pinnedTweetId").value(TestConstants.PINNED_TWEET_ID),
                        jsonPath("$.user.followersSize").value(2L),
                        jsonPath("$.user.followingSize").value(1L),
                        jsonPath("$.user.followerRequestsSize").value(1L),
                        jsonPath("$.user.unreadMessagesCount").value(1L),
                        jsonPath("$.user.isMutedDirectMessages").value(true),
                        jsonPath("$.user.isPrivateProfile").value(false)
                );
    }

    @Test
    @DisplayName("[403] PUT /ui/v1/settings/update/email -Should user email is exist")
    void updateEmail_ShouldUserEmailIsExist() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setEmail("test2023@test.test");
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + EMAIL)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$", is(EMAIL_HAS_ALREADY_BEEN_TAKEN)));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/settings/update/phone - Update phone")
    void updatePhone() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setCountryCode("UK");
        request.setPhone(123456789L);
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + PHONE)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryCode").value("UK"))
                .andExpect(jsonPath("$.phone").value(123456789L));
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/settings/update/phone - Should phone number length lower than 6 digits")
    void updatePhone_ShouldPhoneNumberLengthLowerThan6Digits() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setCountryCode("UK");
        request.setPhone(123L);
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + PHONE)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INVALID_PHONE_NUMBER)));
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/settings/update/phone - Should phone number length more than 10 digits")
    void updatePhone_ShouldPhoneNumberLengthMoreThan10Digits() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setCountryCode("UK");
        request.setPhone(12345678900L);
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + PHONE)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INVALID_PHONE_NUMBER)));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/settings/update/country - Update country")
    void updateCountry() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setCountry("UK");
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + COUNTRY)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("UK")));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/settings/update/gender - Update gender")
    void updateGender() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setGender("Male");
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + GENDER)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Male")));
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/settings/update/gender - Should gender length is 0 characters")
    void updateGender_ShouldGenderLengthIs0() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setGender("");
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + GENDER)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INVALID_GENDER_LENGTH)));
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/settings/update/gender - Should gender length more than 30 characters")
    void updateGender_ShouldGenderLengthMoreThan30() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setGender(TestConstants.LINK_DESCRIPTION);
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + GENDER)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INVALID_GENDER_LENGTH)));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/settings/update/language - Update language")
    void updateLanguage() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setLanguage("English");
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + LANGUAGE)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("English")));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/settings/update/direct - Update direct message requests")
    void updateDirectMessageRequests() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setMutedDirectMessages(false);
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + DIRECT)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/settings/update/private - Update private profile")
    void updatePrivateProfile() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setPrivateProfile(true);
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + PRIVATE)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/settings/update/color_scheme - Update color scheme")
    void updateColorScheme() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setColorScheme(ColorSchemeType.GREEN);
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + COLOR_SCHEME)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("GREEN")));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/settings/update/background_color - Update background color")
    void updateBackgroundColor() throws Exception {
        SettingsRequest request = new SettingsRequest();
        request.setBackgroundColor(BackgroundColorType.DIM);
        mockMvc.perform(put(UI_V1_USER_SETTINGS_UPDATE + BACKGROUND_COLOR)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("DIM")));
    }
}
