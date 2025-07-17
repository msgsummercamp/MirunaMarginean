package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Import(UserControllerTests.MockConfig.class)
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setup() {
        List<User> users = Arrays.asList(
                new User(1L, "john_doe", "password123"),
                new User(2L, "jane_doe", "password456")
        );
        when(userService.findAll()).thenReturn(users);
    }

    @Test
    void getAllUsers_shouldReturnUsersAndStatusOk_whenUsersExist() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$[0].username").value("john_doe"))
                .andExpect(jsonPath("$[1].username").value("jane_doe"));
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }
}
