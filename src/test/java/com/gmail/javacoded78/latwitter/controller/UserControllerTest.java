package com.gmail.javacoded78.latwitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.latwitter.dto.request.UserRequest;
import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;

import static com.gmail.javacoded78.latwitter.util.TestConstants.ABOUT;
import static com.gmail.javacoded78.latwitter.util.TestConstants.AVATAR_ID;
import static com.gmail.javacoded78.latwitter.util.TestConstants.BIRTHDAY;
import static com.gmail.javacoded78.latwitter.util.TestConstants.FULL_NAME;
import static com.gmail.javacoded78.latwitter.util.TestConstants.LOCATION;
import static com.gmail.javacoded78.latwitter.util.TestConstants.REGISTRATION_DATE;
import static com.gmail.javacoded78.latwitter.util.TestConstants.TWEET_COUNT;
import static com.gmail.javacoded78.latwitter.util.TestConstants.URL_USER_BASIC;
import static com.gmail.javacoded78.latwitter.util.TestConstants.USERNAME;
import static com.gmail.javacoded78.latwitter.util.TestConstants.USER_EMAIL;
import static com.gmail.javacoded78.latwitter.util.TestConstants.USER_ID;
import static com.gmail.javacoded78.latwitter.util.TestConstants.WALLPAPER_ID;
import static com.gmail.javacoded78.latwitter.util.TestConstants.WEBSITE;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yml")
@Sql(value = {"/sql/populate-table-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/populate-table-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getUserById() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(true))
                .andExpect(jsonPath("$.profileStarted").value(true));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getUsers() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(6)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].email").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].location").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].website").isNotEmpty())
                .andExpect(jsonPath("$[*].birthday").isNotEmpty())
                .andExpect(jsonPath("$[*].registrationDate").isNotEmpty())
                .andExpect(jsonPath("$[*].tweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].avatar.id").isNotEmpty())
                .andExpect(jsonPath("$[*].wallpaper.id").isNotEmpty())
                .andExpect(jsonPath("$[*].profileCustomized").isNotEmpty())
                .andExpect(jsonPath("$[*].profileStarted").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getRelevantUsers() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/relevant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(5)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].email").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].location").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].website").isNotEmpty())
                .andExpect(jsonPath("$[*].birthday").isNotEmpty())
                .andExpect(jsonPath("$[*].registrationDate").isNotEmpty())
                .andExpect(jsonPath("$[*].tweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].avatar.id").isNotEmpty())
                .andExpect(jsonPath("$[*].wallpaper.id").isNotEmpty())
                .andExpect(jsonPath("$[*].profileCustomized").isNotEmpty())
                .andExpect(jsonPath("$[*].profileStarted").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void searchUsersByUsername() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/search/" + USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(USER_ID))
                .andExpect(jsonPath("$[0].email").value(USER_EMAIL))
                .andExpect(jsonPath("$[0].fullName").value(FULL_NAME))
                .andExpect(jsonPath("$[0].username").value(USERNAME))
                .andExpect(jsonPath("$[0].location").value(LOCATION))
                .andExpect(jsonPath("$[0].about").value(ABOUT))
                .andExpect(jsonPath("$[0].website").value(WEBSITE))
                .andExpect(jsonPath("$[0].birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$[0].registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$[0].tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$[0].avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$[0].wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$[0].profileCustomized").value(true))
                .andExpect(jsonPath("$[0].profileStarted").value(true));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void searchUsersByUsername_NotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/search/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void startUseTwitter() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/" + USER_ID + "/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(true))
                .andExpect(jsonPath("$.profileStarted").value(true));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getUserTweets() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/" + USER_ID + "/tweets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(7)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getUserLikedTweets() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/" + USER_ID + "/liked"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getUserMediaTweets() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/" + USER_ID + "/media"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getUserRetweetsAndReplies() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/" + USER_ID + "/replies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void updateUserProfile() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("test");
        userRequest.setAbout("test");
        userRequest.setLocation("test");
        userRequest.setWebsite("test");

        mockMvc.perform(put(URL_USER_BASIC)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.location").value("test"))
                .andExpect(jsonPath("$.about").value("test"))
                .andExpect(jsonPath("$.website").value("test"))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(true))
                .andExpect(jsonPath("$.profileStarted").value(true));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void uploadImage() throws Exception {
        FileInputStream inputFile = new FileInputStream( "src/test/resources/test.png");
        MockMultipartFile file = new MockMultipartFile("file", "test.png", MediaType.MULTIPART_FORM_DATA_VALUE, inputFile);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(multipart(URL_USER_BASIC + "/upload")
                        .file(file))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void follow() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(50))
                .andExpect(jsonPath("$.email").value("test2019@test.test"))
                .andExpect(jsonPath("$.fullName").value("Vbhjckfd5"))
                .andExpect(jsonPath("$.username").value("Vbhjckfd5"))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").doesNotExist())
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").doesNotExist())
                .andExpect(jsonPath("$.avatar.id").doesNotExist())
                .andExpect(jsonPath("$.wallpaper.id").doesNotExist())
                .andExpect(jsonPath("$.profileCustomized").value(true))
                .andExpect(jsonPath("$.profileStarted").value(false));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void unfollow() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/unfollow/50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(50))
                .andExpect(jsonPath("$.email").value("test2019@test.test"))
                .andExpect(jsonPath("$.fullName").value("Vbhjckfd5"))
                .andExpect(jsonPath("$.username").value("Vbhjckfd5"))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").doesNotExist())
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").doesNotExist())
                .andExpect(jsonPath("$.avatar.id").doesNotExist())
                .andExpect(jsonPath("$.wallpaper.id").doesNotExist())
                .andExpect(jsonPath("$.profileCustomized").value(true))
                .andExpect(jsonPath("$.profileStarted").value(false));
    }
}