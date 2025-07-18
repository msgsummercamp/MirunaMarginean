package com.example.spring_data_jpa.service;

import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


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
    void tearDown() throws Exception {package com.example.spring_data_jpa.service;

import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


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
            void getAllUsers_shouldReturnListOfUsers() {
                List<User> users = Arrays.asList(
                        new User("alice", "alice@example.com", "pass", "Alice", "Smith"),
                        new User("bob", "bob@example.com", "pass", "Bob", "Jones")
                );
                when(userRepository.findAll()).thenReturn(users);

                List<User> result = userService.getAllUsers();

                assertThat(result, hasSize(2));
                assertThat(result.getFirst().getUsername(), is("alice"));
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
            void updateUser_shouldReturnNullIfUserNotFound() {
                when(userRepository.findById(99L)).thenReturn(Optional.empty());

                User result = userService.updateUser(99L, "u", "e", "p", "f", "l");

                assertThat(result, is(nullValue()));
                verify(userRepository, never()).save(any());
            }

            @Test
            void deleteUser_shouldInvokeRepositoryDeleteById() {
                userService.deleteUser(5L);

                verify(userRepository).deleteById(5L);
            }

            @Test
            void getUserByEmail_shouldReturnCorrectUser() {
                User user = new User(1L, "tom", "tom@example.com", "pass", "Tom", "Brown");
                when(userRepository.findByEmail("tom@example.com")).thenReturn(Optional.of(user));

                Optional<User> result = userService.getUserByEmail("tom@example.com");

                assertThat(result.isPresent(), is(true));
                assertThat(result.get().getUsername(), is("tom"));
            }

            @Test
            void getUserByUsername_shouldReturnCorrectUser() {
                User user = new User(1L, "lucy", "lucy@example.com", "pass", "Lucy", "Gray");
                when(userRepository.findByUsername("lucy")).thenReturn(Optional.of(user));

                Optional<User> result = userService.getUserByUsername("lucy");

                assertThat(result.isPresent(), is(true));
                assertThat(result.get().getUsername(), is("lucy"));
            }

            @Test
            void countUsers_shouldReturnCorrectCount() {
                when(userRepository.countUsers()).thenReturn(5L);

                long count = userService.countUsers();

                assertThat(count, is(5L));
                verify(userRepository, times(1)).countUsers();
            }
        }

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
    void getAllUsers_shouldReturnListOfUsers() {
        List<User> users = Arrays.asList(
                new User("alice", "alice@example.com", "pass", "Alice", "Smith"),
                new User("bob", "bob@example.com", "pass", "Bob", "Jones")
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertThat(result, hasSize(2));
        assertThat(result.getFirst().getUsername(), is("alice"));
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
    void updateUser_shouldReturnNullIfUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        User result = userService.updateUser(99L, "u", "e", "p", "f", "l");

        assertThat(result, is(nullValue()));
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_shouldInvokeRepositoryDeleteById() {
        userService.deleteUser(5L);

        verify(userRepository).deleteById(5L);
    }

    @Test
    void getUserByEmail_shouldReturnCorrectUser() {
        User user = new User(1L, "tom", "tom@example.com", "pass", "Tom", "Brown");
        when(userRepository.findByEmail("tom@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByEmail("tom@example.com");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getUsername(), is("tom"));
    }

    @Test
    void getUserByUsername_shouldReturnCorrectUser() {
        User user = new User(1L, "lucy", "lucy@example.com", "pass", "Lucy", "Gray");
        when(userRepository.findByUsername("lucy")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername("lucy");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getUsername(), is("lucy"));
    }

    @Test
    void countUsers_shouldReturnCorrectCount() {
        when(userRepository.countUsers()).thenReturn(5L);

        long count = userService.countUsers();

        assertThat(count, is(5L));
        verify(userRepository, times(1)).countUsers();
    }
}
