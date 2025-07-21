package com.example.spring_security.controller;

import com.example.spring_security.dto.UserPatchRequest;
import com.example.spring_security.dto.UserRequest;
import com.example.spring_security.model.User;
import com.example.spring_security.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.*;


@Tag(name = "User Management", description = "Operations related to user management")
@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Returns a paginated list of users")
    @GetMapping
    @ResponseBody
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return userService.getAllUsers(PageRequest.of(page, size));
    }

    @Operation(summary = "Create a new user", responses = {
            @ApiResponse(responseCode = "201", description = "User created")
    })
    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get a user")
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Update a user")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a user")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Operation(summary = "Patch a user")
    @PatchMapping("/{id}")
    public User patchUser(@PathVariable Long id, @Valid @RequestBody UserPatchRequest patchRequest) {
        return userService.patchUser(id, patchRequest);
    }
}
