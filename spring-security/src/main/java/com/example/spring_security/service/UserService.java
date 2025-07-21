package com.example.spring_security.service;

import com.example.spring_security.dto.UserPatchRequest;
import com.example.spring_security.dto.UserRequest;
import com.example.spring_security.exceptions.UserNotFoundException;
import com.example.spring_security.model.Role;
import com.example.spring_security.model.User;
import com.example.spring_security.repository.RoleRepository;
import com.example.spring_security.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserRequest userRequest) {
        User user = new User();
        Set<Role> userRoles = userRequest.getRoles().stream()
                .map(roleDto -> roleRepository.findByName(roleDto.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleDto.getName())))
                .collect(Collectors.toSet());

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());

        user.setRoles(userRoles);

        userRepository.save(user);
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
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
        if (patchRequest.getPassword() != null) user.setPassword(passwordEncoder.encode(patchRequest.getPassword()));
        if (patchRequest.getFirstname() != null) user.setFirstname(patchRequest.getFirstname());
        if (patchRequest.getLastname() != null) user.setLastname(patchRequest.getLastname());
        return userRepository.save(user);
    }
}
