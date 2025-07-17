package com.example.backend.repository;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTests {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    void save_addsUserToRepository() {
        User user = new User(1L, "john_doe", "pass123");

        userRepository.save(user);
        List<User> users = userRepository.findAll();

        assertEquals(1, users.size());
        assertEquals("john_doe", users.get(0).getUsername());
    }

    @Test
    void findById_returnsCorrectUser() {
        User user = new User(2L, "jane_doe", "pass456");
        userRepository.save(user);

        User found = userRepository.findById(2L);

        assertNotNull(found);
        assertEquals("jane_doe", found.getUsername());
    }

    @Test
    void findById_returnsNullIfNotFound() {
        User result = userRepository.findById(99L);
        assertNull(result);
    }

    @Test
    void deleteById_removesUser() {
        userRepository.save(new User(3L, "john", "a"));
        userRepository.save(new User(4L, "jane", "b"));

        userRepository.deleteById(3L);

        List<User> users = userRepository.findAll();
        assertEquals(1, users.size());
        assertEquals("jane", users.get(0).getUsername());
    }

    @Test
    void findAll_returnsAllUsers() {
        userRepository.save(new User(1L, "a", "1"));
        userRepository.save(new User(2L, "b", "2"));

        List<User> all = userRepository.findAll();

        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(u -> u.getUsername().equals("a")));
        assertTrue(all.stream().anyMatch(u -> u.getUsername().equals("b")));
    }
}
