package com.example.spring_boot_rest_api.service;

import com.example.spring_boot_rest_api.dto.UserPatchRequest;
import com.example.spring_boot_rest_api.exceptions.UserNotFoundException;
import com.example.spring_boot_rest_api.model.User;
import com.example.spring_boot_rest_api.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasSize;

class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void createUser_shouldSaveUser() {
        User user = new User("john", "john@example.com", "pass123", "John", "Doe");

        userService.createUser(user.getUsername(), user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname());

        verify(userRepository, times(1)).save(argThat(savedUser ->
                savedUser.getUsername().equals("john") &&
                        savedUser.getEmail().equals("john@example.com")
        ));
    }

    @Test
    void getAllUsers_shouldReturnPageOfUsers() {
        List<User> users = List.of(
                new User("alice", "alice@example.com", "pass", "Alice", "Smith"),
                new User("bob",   "bob@example.com",   "pass", "Bob",   "Jones")
        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(users, pageable, users.size());

        when(userRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        Page<User> result = userService.getAllUsers(pageable);

        assertThat(result.getContent(), hasSize(2));
        assertThat(result.getContent().getFirst().getUsername(), is("alice"));
    }

    @Test
    void getUserById_shouldReturnUserIfExists() {
        User user = new User(1L, "jane", "jane@example.com", "pass", "Jane", "Doe");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getUsername(), is("jane"));
    }

    @Test
    void updateUser_shouldModifyAndSaveUserIfExists() {
        User existing = new User(1L, "old", "old@example.com", "123", "Old", "Name");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        User updated = userService.updateUser(1L, "new", "new@example.com", "456", "New", "Name");

        assertThat(updated, is(notNullValue()));
        assertThat(updated.getUsername(), is("new"));
        verify(userRepository).save(existing);
    }

    @Test
    void updateUser_shouldThrowExceptionIfUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                userService.updateUser(99L, "u", "e", "p", "f", "l")
        );

        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_shouldInvokeRepositoryDeleteById() {
        userService.deleteUser(5L);

        verify(userRepository).deleteById(5L);
    }

    @Test
    void patchUser_shouldUpdateFieldsIfPresent() {
        User existing = new User(1L, "old", "old@example.com", "123", "Old", "Name");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserPatchRequest patchRequest = new UserPatchRequest();
        patchRequest.setUsername("newUsername");
        patchRequest.setEmail("new@example.com");

        User patched = userService.patchUser(1L, patchRequest);

        assertThat(patched.getUsername(), is("newUsername"));
        assertThat(patched.getEmail(), is("new@example.com"));
        assertThat(patched.getPassword(), is("123"));
        assertThat(patched.getFirstname(), is("Old"));
        assertThat(patched.getLastname(), is("Name"));
        verify(userRepository).save(existing);
    }
}
