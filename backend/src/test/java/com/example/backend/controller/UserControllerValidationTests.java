package com.example.backend.controller;

import com.example.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(UserControllerValidationTests.TestConfig.class)
public class UserControllerValidationTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_withValidInput_returnsCreated() throws Exception {
        String validJson = """
            {
                "id": 1,
                "username": "john123",
                "password": "password123"
            }
            """;

        doNothing().when(userService).createUser(1L, "john123", "password123");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isCreated());
    }

    @Test
    void createUser_withNullId_returnsBadRequest() throws Exception {
        String invalidJson = """
            {
                "username": "john123",
                "password": "password123"
            }
            """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='id')].defaultMessage").value("Id is required"));
    }

    @Test
    void createUser_withInvalidUsername_returnsBadRequest() throws Exception {
        String invalidJson = """
            {
                "id": 1,
                "username": "jo!",
                "password": "password123"
            }
            """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='username')].defaultMessage").value("Username must be 3-15 alphanumeric characters"));
    }

    @Test
    void createUser_withShortPassword_returnsBadRequest() throws Exception {
        String invalidJson = """
            {
                "id": 1,
                "username": "john123",
                "password": "short"
            }
            """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='password')].defaultMessage").value("Password must be at least 8 characters"));
    }

    static class TestConfig {
        @Bean
        public UserService userService() {
            return org.mockito.Mockito.mock(UserService.class);
        }
    }
}
