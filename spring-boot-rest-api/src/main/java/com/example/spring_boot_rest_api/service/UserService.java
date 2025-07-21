package com.example.spring_boot_rest_api.service;

import com.example.spring_boot_rest_api.dto.UserPatchRequest;
import com.example.spring_boot_rest_api.dto.UserRequest;
import com.example.spring_boot_rest_api.exceptions.UserNotFoundException;
import com.example.spring_boot_rest_api.model.User;
import com.example.spring_boot_rest_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserRequest userRequest) {
        User user = new User(
                userRequest.getUsername(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getFirstname(),
                userRequest.getLastname()
        );
        userRepository.save(user);
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(user.getLastname());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User patchUser(Long id, UserPatchRequest patchRequest) {
        User user = userRepository.findById(id).orElseThrow();
        if (patchRequest.getUsername() != null) user.setUsername(patchRequest.getUsername());
        if (patchRequest.getEmail() != null) user.setEmail(patchRequest.getEmail());
        if (patchRequest.getPassword() != null) user.setPassword(patchRequest.getPassword());
        if (patchRequest.getFirstname() != null) user.setFirstname(patchRequest.getFirstname());
        if (patchRequest.getLastname() != null) user.setLastname(patchRequest.getLastname());
        return userRepository.save(user);
    }
}
