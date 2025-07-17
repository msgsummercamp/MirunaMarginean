package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    void createUser_shouldSaveUser_whenValidInput() {
        userService.createUser(1L, "john_doe", "password123");

        verify(userRepository).save(userCaptor.capture());
        User saved = userCaptor.getValue();
        assertEquals("john_doe", saved.getUsername());
        assertEquals("password123", saved.getPassword());
        assertEquals(1L, saved.getId());
    }

    @Test
    void deleteUser_shouldDeleteUser_whenIdExists() {
        userService.deleteUser(42L);
        verify(userRepository).deleteById(42L);
    }

    @Test
    void getUser_shouldReturnUser_whenUserExists() {
        User mockUser = new User(5L, "test_user", "secret");
        when(userRepository.findById(5L)).thenReturn(mockUser);

        User result = userService.getUser(5L);

        assertNotNull(result);
        assertEquals("test_user", result.getUsername());
    }

    @Test
    void findAll_shouldReturnAllUsers_whenUsersExist() {
        List<User> users = Arrays.asList(
                new User(1L, "john", "a"),
                new User(2L, "jane", "b")
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        assertEquals("jane", result.get(1).getUsername());
    }
}
