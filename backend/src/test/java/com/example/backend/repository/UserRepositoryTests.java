package com.example.backend.repository;

import com.example.backend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class UserRepositoryTests {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    void save_shouldAddUser_whenUserIsGiven() {
        User user = new User(1L, "john_doe", "pass123");

        userRepository.save(user);
        List<User> users = userRepository.findAll();

        assertThat(users, hasSize(1));
        assertThat(users.getFirst().getUsername(), is("john_doe"));
    }

    @Test
    void findById_shouldReturnUser_whenUserExists() {
        User user = new User(2L, "jane_doe", "pass456");
        userRepository.save(user);

        User found = userRepository.findById(2L);

        assertThat(found, is(notNullValue()));
        assertThat(found.getUsername(), is("jane_doe"));
    }

    @Test
    void findById_shouldReturnNull_whenUserDoesNotExist() {
        User result = userRepository.findById(99L);

        assertThat(result, is(nullValue()));
    }

    @Test
    void deleteById_shouldRemoveUser_whenUserExists() {
        userRepository.save(new User(3L, "john", "a"));
        userRepository.save(new User(4L, "jane", "b"));

        userRepository.deleteById(3L);

        List<User> users = userRepository.findAll();
        assertThat(users, hasSize(1));
        assertThat(users.getFirst().getUsername(), is("jane"));
    }

    @Test
    void findAll_shouldReturnAllUsers_whenUsersExist() {
        userRepository.save(new User(1L, "a", "1"));
        userRepository.save(new User(2L, "b", "2"));

        List<User> all = userRepository.findAll();

        assertThat(all, hasSize(2));
        assertThat(all, hasItem(hasProperty("username", is("a"))));
        assertThat(all, hasItem(hasProperty("username", is("b"))));
    }
}
