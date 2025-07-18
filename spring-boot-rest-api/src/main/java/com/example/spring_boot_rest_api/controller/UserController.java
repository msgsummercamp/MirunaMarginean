package com.example.spring_boot_rest_api.controller;

import com.example.spring_boot_rest_api.dto.UserPatchRequest;
import com.example.spring_boot_rest_api.dto.UserRequest;
import com.example.spring_boot_rest_api.model.User;
import com.example.spring_boot_rest_api.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.*;
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
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return userService.getAllUsers(PageRequest.of(page, size));
    }

    @Operation(summary = "Create a new user", responses = {
            @ApiResponse(responseCode = "201", description = "User created")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserRequest userRequest) {
        userService.createUser(userRequest.getUsername(), userRequest.getEmail(), userRequest.getPassword(), userRequest.getFirstname(), userRequest.getLastname());
    }

    @Operation(summary = "Update a user")
    @PutMapping
    public User updateUser(@RequestParam Long id, @Valid @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest.getUsername(), userRequest.getEmail(), userRequest.getPassword(), userRequest.getFirstname(), userRequest.getLastname());
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping
    public void deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
    }

    @Operation(summary = "Patch a user")
    @PatchMapping
    public User patchUser(@RequestParam Long id, @Valid @RequestBody UserPatchRequest patchRequest) {
        return userService.patchUser(id, patchRequest);
    }
}
