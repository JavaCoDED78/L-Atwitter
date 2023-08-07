package com.gmail.javacoded78.controller.api;

import com.gmail.javacoded78.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_LISTS;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID;
import static com.gmail.javacoded78.util.TestConstants.LIST_NAME;
import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = "/sql-test/populate-lists-db.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "/sql-test/clear-lists-db.sql", executionPhase = AFTER_TEST_METHOD)
public class ListsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /api/v1/lists/4 - Get Notification List")
    public void getNotificationList() throws Exception {
        mockMvc.perform(get(API_V1_LISTS + LIST_ID, 4)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value(TestConstants.LIST_NAME));
    }
}