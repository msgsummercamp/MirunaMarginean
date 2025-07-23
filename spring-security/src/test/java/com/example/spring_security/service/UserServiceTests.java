package com.example.spring_security.service;

import com.example.spring_security.dto.UserPatchRequest;
import com.example.spring_security.dto.UserRequest;
import com.example.spring_security.exceptions.UserNotFoundException;
import com.example.spring_security.model.Role;
import com.example.spring_security.model.User;
import com.example.spring_security.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

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
    void createUser_shouldSaveUserWithRole() {
        UserRequest user = new UserRequest();
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setRoles(Set.of("ROLE_USER"));

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        userService.createUser(user);

        verify(userRepository, times(1)).save(argThat(savedUser ->
                savedUser.getUsername().equals("john") &&
                        savedUser.getEmail().equals("john@example.com") &&
                        savedUser.getPassword().equals("encodedPassword") &&
                        savedUser.getFirstname().equals("John") &&
                        savedUser.getLastname().equals("Doe") &&
                        savedUser.getRoles() != null &&
                        savedUser.getRoles().contains(Role.ROLE_USER)
        ));
    }

    @Test
    void getAllUsers_shouldReturnPageOfUsers() {
        List<User> users = List.of(
                new User("alice", "alice@example.com", "pass", "Alice", "Smith"),
                new User("bob", "bob@example.com", "pass", "Bob", "Jones")
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

        User result = userService.getUserById(1L);

        assertThat(result.getUsername(), is("jane"));
    }

    @Test
    void updateUser_shouldModifyAndSaveUserIfExists() {
        User existingUser = new User(1L, "old", "old@example.com", "123", "Old", "Name", Set.of(Role.ROLE_USER));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserRequest existing = new UserRequest();

        existing.setId(1L);
        existing.setUsername("new");
        existing.setEmail("new@example.com");
        existing.setPassword("password");
        existing.setFirstname("New");
        existing.setLastname("Name");
        existing.setRoles(Set.of("ROLE_USER", "ROLE_ADMIN"));

        User updated = userService.updateUser(existing);

        assertThat(updated, is(notNullValue()));
        assertThat(updated.getUsername(), is("new"));
        assertThat(updated.getEmail(), is("new@example.com"));
        assertThat(updated.getPassword(), is(passwordEncoder.encode("password")));
        assertThat(updated.getFirstname(), is("New"));
        assertThat(updated.getLastname(), is("Name"));
        assertThat(updated.getRoles(), hasSize(2));
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_shouldThrowExceptionIfUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        UserRequest userRequest = new UserRequest();

        userRequest.setId(99L);
        userRequest.setUsername("u");
        userRequest.setEmail("e");
        userRequest.setPassword("p");
        userRequest.setFirstname("f");
        userRequest.setLastname("l");

        assertThrows(UserNotFoundException.class, () ->
                userService.updateUser(userRequest)
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
        User existing = new User(1L, "old", "old@example.com", "password", "Old", "Name", Set.of(Role.ROLE_USER));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserPatchRequest patchRequest = new UserPatchRequest();
        patchRequest.setId(1L);
        patchRequest.setUsername("newUsername");
        patchRequest.setEmail("new@example.com");

        User patched = userService.patchUser(patchRequest);

        assertThat(patched.getUsername(), is("newUsername"));
        assertThat(patched.getEmail(), is("new@example.com"));
        assertThat(patched.getPassword(), is("password"));
        assertThat(patched.getFirstname(), is("Old"));
        assertThat(patched.getLastname(), is("Name"));
        assertThat(patched.getRoles(), hasSize(1));
        verify(userRepository).save(existing);
    }
}
