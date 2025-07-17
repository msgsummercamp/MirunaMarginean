package com.example.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("UserService initialized with UserRepository");
    }

    public void hardcodeAddUsers() {
        logger.info("Adding hardcoded users to the repository");
        createUser(1L, "john_doe", "password123");
        createUser(2L, "jane_doe", "password456");
    }

    public void createUser(Long id, String username, String password) {
        logger.debug("Creating user with username: {}", username);
        User user = new User(id, username, password);
        userRepository.save(user);
        logger.info("User created with username: {}", username);
    }

    public void deleteUser(Long id) {
        logger.debug("Deleting user with id: {}", id);
        userRepository.deleteById(id);
        logger.info("User with id {} deleted", id);
    }

    public User getUser(Long id) {
        logger.debug("Retrieving user with id: {}", id);
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        logger.info("Retrieving all users from the repository");
        return userRepository.findAll();
    }
}