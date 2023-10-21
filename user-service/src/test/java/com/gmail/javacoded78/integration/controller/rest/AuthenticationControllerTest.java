package com.gmail.javacoded78.integration.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.AuthenticationRequest;
import com.gmail.javacoded78.dto.request.CurrentPasswordResetRequest;
import com.gmail.javacoded78.dto.request.PasswordResetRequest;
import com.gmail.javacoded78.dto.request.ProcessEmailRequest;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_NOT_VALID;
import static com.gmail.javacoded78.constants.ErrorMessage.EMPTY_CURRENT_PASSWORD;
import static com.gmail.javacoded78.constants.ErrorMessage.EMPTY_PASSWORD;
import static com.gmail.javacoded78.constants.ErrorMessage.EMPTY_PASSWORD_CONFIRMATION;
import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_PASSWORD;
import static com.gmail.javacoded78.constants.ErrorMessage.INVALID_PASSWORD_RESET_CODE;
import static com.gmail.javacoded78.constants.ErrorMessage.PASSWORDS_NOT_MATCH;
import static com.gmail.javacoded78.constants.ErrorMessage.SHORT_PASSWORD;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.FORGOT;
import static com.gmail.javacoded78.constants.PathConstants.FORGOT_EMAIL;
import static com.gmail.javacoded78.constants.PathConstants.LOGIN;
import static com.gmail.javacoded78.constants.PathConstants.RESET;
import static com.gmail.javacoded78.constants.PathConstants.RESET_CODE;
import static com.gmail.javacoded78.constants.PathConstants.RESET_CURRENT;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_AUTH;
import static com.gmail.javacoded78.util.TestConstants.REGISTRATION_DATE;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class AuthenticationControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    void init() {
        authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(TestConstants.USER_EMAIL);
    }

    @Test
    @DisplayName("[200] POST /ui/v1/auth/login - Login")
    void login() throws Exception {
        authenticationRequest.setPassword(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + LOGIN)
                        .content(mapper.writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/login - Should Email not valid")
    void login_ShouldEmailNotValid() throws Exception {
        authenticationRequest.setEmail("notvalidemail@test");
        authenticationRequest.setPassword(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + LOGIN)
                        .content(mapper.writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is(EMAIL_NOT_VALID)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/login - Should password is empty")
    void login_ShouldPasswordIsEmpty() throws Exception {
        authenticationRequest.setPassword(null);
        mockMvc.perform(post(UI_V1_AUTH + LOGIN)
                        .content(mapper.writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", is(EMPTY_PASSWORD)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/login - Should password less then 8 characters")
    void login_ShouldPasswordLessThen8Characters() throws Exception {
        authenticationRequest.setPassword("test123");
        mockMvc.perform(post(UI_V1_AUTH + LOGIN)
                        .content(mapper.writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", is(SHORT_PASSWORD)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/auth/forgot/email - Find existing email")
    void findExistingEmail() throws Exception {
        ProcessEmailRequest request = new ProcessEmailRequest();
        request.setEmail(TestConstants.USER_EMAIL);
        mockMvc.perform(post(UI_V1_AUTH + FORGOT_EMAIL)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Reset password code is send to your E-mail")));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/forgot/email - Should email not valid")
    void findExistingEmail_ShouldEmailNotValid() throws Exception {
        ProcessEmailRequest request = new ProcessEmailRequest();
        request.setEmail("test2023@test");
        mockMvc.perform(post(UI_V1_AUTH + FORGOT_EMAIL)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is(EMAIL_NOT_VALID)));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/auth/forgot/email - Email not found")
    void findExistingEmail_EmailNotFound() throws Exception {
        ProcessEmailRequest request = new ProcessEmailRequest();
        request.setEmail(TestConstants.NOT_VALID_EMAIL);
        mockMvc.perform(post(UI_V1_AUTH + FORGOT_EMAIL)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(EMAIL_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/auth/forgot - Send password reset code")
    void sendPasswordResetCode() throws Exception {
        ProcessEmailRequest request = new ProcessEmailRequest();
        request.setEmail(TestConstants.USER_EMAIL);
        mockMvc.perform(post(UI_V1_AUTH + FORGOT)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Reset password code is send to your E-mail")));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/forgot - Should email not valid")
    void sendPasswordResetCode_ShouldEmailNotValid() throws Exception {
        ProcessEmailRequest request = new ProcessEmailRequest();
        request.setEmail("test2023@test");
        mockMvc.perform(post(UI_V1_AUTH + FORGOT)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is(EMAIL_NOT_VALID)));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/auth/forgot - Should email Not Found")
    void sendPasswordResetCode_ShouldEmailNotFound() throws Exception {
        ProcessEmailRequest request = new ProcessEmailRequest();
        request.setEmail(TestConstants.NOT_VALID_EMAIL);
        mockMvc.perform(post(UI_V1_AUTH + FORGOT)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(EMAIL_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/auth/reset/1234567890 - Get user by reset code")
    void getUserByResetCode() throws Exception {
        mockMvc.perform(get(UI_V1_AUTH + RESET_CODE, 1234567890))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(3),
                        jsonPath("$.email").value("test2016@test.test"),
                        jsonPath("$.fullName").value(TestConstants.FULL_NAME),
                        jsonPath("$.username").value(TestConstants.USERNAME),
                        jsonPath("$.location").value(TestConstants.LOCATION),
                        jsonPath("$.about").value(TestConstants.ABOUT),
                        jsonPath("$.website").value(TestConstants.WEBSITE),
                        jsonPath("$.birthday").value(TestConstants.BIRTHDAY),
                        jsonPath("$.registrationDate").value(REGISTRATION_DATE),
                        jsonPath("$.tweetCount").value(TestConstants.TWEET_COUNT),
                        jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.wallpaper").value(TestConstants.WALLPAPER_SRC),
                        jsonPath("$.profileCustomized").value(true),
                        jsonPath("$.profileStarted").value(true)
                );
    }

    @Test
    @DisplayName("[400] GET /ui/v1/auth/reset/test123 - Get user by reset code bad request")
    void getUserByResetCode_BadRequest() throws Exception {
        mockMvc.perform(get(UI_V1_AUTH + RESET_CODE, "test123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INVALID_PASSWORD_RESET_CODE)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/auth/reset - Reset password")
    void passwordReset() throws Exception {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setEmail(TestConstants.USER_EMAIL);
        passwordResetRequest.setPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword2(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + RESET)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Password successfully changed!")));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/auth/reset - Should user Not Found by email")
    void passwordReset_ShouldUserNotFoundByEmail() throws Exception {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setEmail(TestConstants.NOT_VALID_EMAIL);
        passwordResetRequest.setPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword2(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + RESET)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.email", is(EMAIL_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset - Should Email not valid")
    void passwordReset_ShouldEmailNotValid() throws Exception {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setEmail("notvalidemail@test");
        passwordResetRequest.setPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword2(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + RESET)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is(EMAIL_NOT_VALID)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset - Should password be empty")
    void passwordReset_ShouldPasswordBeEmpty() throws Exception {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setEmail(TestConstants.USER_EMAIL);
        passwordResetRequest.setPassword2(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + RESET)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", is(EMPTY_PASSWORD)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset - Should password2 be empty")
    void passwordReset_ShouldPassword2BeEmpty() throws Exception {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setEmail(TestConstants.USER_EMAIL);
        passwordResetRequest.setPassword(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + RESET)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password2", is(EMPTY_PASSWORD)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset - Should password less then 8 characters")
    void passwordReset_ShouldPasswordLessThen8Characters() throws Exception {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setEmail(TestConstants.USER_EMAIL);
        passwordResetRequest.setPassword("qwerty");
        passwordResetRequest.setPassword2(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + RESET)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", is(SHORT_PASSWORD)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset - Should password2 less then 8 characters")
    void passwordReset_ShouldPassword2LessThen8Characters() throws Exception {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setEmail(TestConstants.USER_EMAIL);
        passwordResetRequest.setPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword2("qwerty");
        mockMvc.perform(post(UI_V1_AUTH + RESET)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password2", is(SHORT_PASSWORD)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset - Should passwords not match")
    void passwordReset_ShouldPasswordsNotMatch() throws Exception {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setEmail(TestConstants.USER_EMAIL);
        passwordResetRequest.setPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword2("test1234");
        mockMvc.perform(post(UI_V1_AUTH + RESET)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", is(PASSWORDS_NOT_MATCH)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/auth/reset/current - Current Password Reset")
    void currentPasswordReset() throws Exception {
        CurrentPasswordResetRequest passwordResetRequest = new CurrentPasswordResetRequest();
        passwordResetRequest.setCurrentPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword2(TestConstants.PASSWORD);

        mockMvc.perform(post(UI_V1_AUTH + RESET_CURRENT)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Your password has been successfully updated.")));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset/current - Should current password is empty")
    void currentPasswordReset_ShouldCurrentPasswordIsEmpty() throws Exception {
        CurrentPasswordResetRequest passwordResetRequest = new CurrentPasswordResetRequest();
        passwordResetRequest.setCurrentPassword("");
        passwordResetRequest.setPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword2(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + RESET_CURRENT)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.currentPassword", is(EMPTY_CURRENT_PASSWORD)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset/current - Should password is empty")
    void currentPasswordReset_ShouldPasswordIsEmpty() throws Exception {
        CurrentPasswordResetRequest passwordResetRequest = new CurrentPasswordResetRequest();
        passwordResetRequest.setCurrentPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword(null);
        passwordResetRequest.setPassword2(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + RESET_CURRENT)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", is(EMPTY_PASSWORD)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset/current - Should password2 is empty")
    void currentPasswordReset_ShouldPassword2IsEmpty() throws Exception {
        CurrentPasswordResetRequest passwordResetRequest = new CurrentPasswordResetRequest();
        passwordResetRequest.setCurrentPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword2(null);
        mockMvc.perform(post(UI_V1_AUTH + RESET_CURRENT)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password2", is(EMPTY_PASSWORD_CONFIRMATION)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset/current - Should password less then 8 characters")
    void currentPasswordReset_ShouldPasswordLessThen8Characters() throws Exception {
        CurrentPasswordResetRequest passwordResetRequest = new CurrentPasswordResetRequest();
        passwordResetRequest.setCurrentPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword("test");
        passwordResetRequest.setPassword2(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + RESET_CURRENT)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", is(SHORT_PASSWORD)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset/current - Should password2 less then 8 characters")
    void currentPasswordReset_ShouldPassword2LessThen8Characters() throws Exception {
        CurrentPasswordResetRequest passwordResetRequest = new CurrentPasswordResetRequest();
        passwordResetRequest.setCurrentPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword2("test");
        mockMvc.perform(post(UI_V1_AUTH + RESET_CURRENT)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password2", is(SHORT_PASSWORD)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset/current - Should current password reset not found")
    void currentPasswordReset_ShouldCurrentPasswordResetNotFound() throws Exception {
        CurrentPasswordResetRequest passwordResetRequest = new CurrentPasswordResetRequest();
        passwordResetRequest.setCurrentPassword("qwerty123456");
        passwordResetRequest.setPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword2(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + RESET_CURRENT)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.currentPassword", is(INCORRECT_PASSWORD)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/auth/reset/current - Should passwords not match")
    void currentPasswordReset_ShouldPasswordsNotMatch() throws Exception {
        CurrentPasswordResetRequest passwordResetRequest = new CurrentPasswordResetRequest();
        passwordResetRequest.setCurrentPassword(TestConstants.PASSWORD);
        passwordResetRequest.setPassword("qwerty123456");
        passwordResetRequest.setPassword2(TestConstants.PASSWORD);
        mockMvc.perform(post(UI_V1_AUTH + RESET_CURRENT)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(passwordResetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", is(PASSWORDS_NOT_MATCH)));
    }
}