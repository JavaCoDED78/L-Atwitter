package com.gmail.javacoded78.integration.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.AuthenticationRequest;
import com.gmail.javacoded78.dto.request.ProcessEmailRequest;
import com.gmail.javacoded78.dto.request.RegistrationRequest;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.ErrorMessage.ACTIVATION_CODE_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.BLANK_NAME;
import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_HAS_ALREADY_BEEN_TAKEN;
import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_NOT_VALID;
import static com.gmail.javacoded78.constants.ErrorMessage.NAME_NOT_VALID;
import static com.gmail.javacoded78.constants.ErrorMessage.SHORT_PASSWORD;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.PathConstants.REGISTRATION_ACTIVATE_CODE;
import static com.gmail.javacoded78.constants.PathConstants.REGISTRATION_CHECK;
import static com.gmail.javacoded78.constants.PathConstants.REGISTRATION_CODE;
import static com.gmail.javacoded78.constants.PathConstants.REGISTRATION_CONFIRM;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_AUTH;
import static com.gmail.javacoded78.util.TestConstants.REGISTRATION_DATE;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class RegistrationControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    private AuthenticationRequest authenticationRequest;
    private RegistrationRequest registrationRequest;

    @BeforeEach
    void init() {
        authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(TestConstants.USER_EMAIL);

        registrationRequest = new RegistrationRequest();
        registrationRequest.setEmail("testtest@test.com");
        registrationRequest.setUsername("testtest");
        registrationRequest.setBirthday(TestConstants.BIRTHDAY);
    }

    @Test
    @DisplayName("[200] POST /ui/v1/auth/registration/check - Check Email")
    void checkEmail() throws Exception {
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CHECK)
                        .content(mapper.writeValueAsString(registrationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("User data checked.")));
    }

    @Test
    @DisplayName("[403] POST /ui/v1/auth/registration/check - Should user email is exist")
    void checkEmail_ShouldUserEmailIsExist() throws Exception {
        registrationRequest.setEmail(TestConstants.USER_EMAIL);
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CHECK)
                        .content(mapper.writeValueAsString(registrationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$", is(EMAIL_HAS_ALREADY_BEEN_TAKEN)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/registration/check - Should email not valid")
    void checkEmail_ShouldEmailNotValid() throws Exception {
        registrationRequest.setEmail("test2015@test");
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CHECK)
                        .content(mapper.writeValueAsString(registrationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is(EMAIL_NOT_VALID)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/registration/check - Should username is empty")
    void checkEmail_ShouldUsernameIsEmpty() throws Exception {
        registrationRequest.setUsername(null);
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CHECK)
                        .content(mapper.writeValueAsString(registrationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username", is(BLANK_NAME)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/registration/check - Should username more then 50 characters")
    void checkEmail_ShouldUsernameMoreThen50Characters() throws Exception {
        registrationRequest.setUsername("qwertqwertqwertqwertqwertqwertqwertqwertqwertqwert123");
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CHECK)
                        .content(mapper.writeValueAsString(registrationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username", is(NAME_NOT_VALID)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/auth/registration/code - Send registration code")
    void sendRegistrationCode() throws Exception {
        ProcessEmailRequest request = new ProcessEmailRequest();
        request.setEmail(TestConstants.USER_EMAIL);
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CODE)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Registration code sent successfully")));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/registration/code - Should email not valid")
    void sendRegistrationCode_ShouldEmailNotValid() throws Exception {
        ProcessEmailRequest request = new ProcessEmailRequest();
        request.setEmail("test2015@test");
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CODE)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is(EMAIL_NOT_VALID)));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/auth/registration/code - User not found")
    void sendRegistrationCode_ShouldUserNotFound() throws Exception {
        ProcessEmailRequest request = new ProcessEmailRequest();
        request.setEmail(TestConstants.NOT_VALID_EMAIL);
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CODE)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/auth/registration/activate/1234567890 - Check registration code")
    void checkRegistrationCode() throws Exception {
        mockMvc.perform(get(UI_V1_AUTH + REGISTRATION_ACTIVATE_CODE, 1234567890))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("User successfully activated.")));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/auth/registration/activate/test - Registration code not found")
    void checkRegistrationCode_NotFound() throws Exception {
        mockMvc.perform(get(UI_V1_AUTH + REGISTRATION_ACTIVATE_CODE, "test"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(ACTIVATION_CODE_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/auth/registration/confirm - End registration")
    void endRegistration() throws Exception {
        authenticationRequest.setPassword(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CONFIRM)
                        .content(mapper.writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.user.id").value(TestConstants.USER_ID),
                        jsonPath("$.user.email").value(TestConstants.USER_EMAIL),
                        jsonPath("$.user.fullName").value(TestConstants.FULL_NAME),
                        jsonPath("$.user.username").value(TestConstants.USERNAME),
                        jsonPath("$.user.location").value(TestConstants.LOCATION),
                        jsonPath("$.user.about").value(TestConstants.ABOUT),
                        jsonPath("$.user.website").value(TestConstants.WEBSITE),
                        jsonPath("$.user.birthday").value(TestConstants.BIRTHDAY),
                        jsonPath("$.user.registrationDate").value(REGISTRATION_DATE),
                        jsonPath("$.user.tweetCount").value(TestConstants.TWEET_COUNT),
                        jsonPath("$.user.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.user.wallpaper").value(TestConstants.WALLPAPER_SRC),
                        jsonPath("$.user.profileCustomized").value(true),
                        jsonPath("$.user.profileStarted").value(true)
                );
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/registration/confirm - Should email not valid")
    void endRegistration_ShouldEmailNotValid() throws Exception {
        authenticationRequest.setEmail("test2023@test");
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CONFIRM)
                        .content(mapper.writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is(EMAIL_NOT_VALID)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/registration/confirm - Should short password")
    void endRegistration_ShouldShortPassword() throws Exception {
        authenticationRequest.setPassword("123");
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CONFIRM)
                        .content(mapper.writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", is(SHORT_PASSWORD)));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/auth/registration/confirm - Should user Not Found by email")
    void endRegistration_ShouldUserNotFound() throws Exception {
        authenticationRequest.setEmail(TestConstants.NOT_VALID_EMAIL);
        authenticationRequest.setPassword(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + REGISTRATION_CONFIRM)
                        .content(mapper.writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }
}
