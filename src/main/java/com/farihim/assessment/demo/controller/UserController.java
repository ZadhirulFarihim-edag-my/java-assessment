package com.farihim.assessment.demo.controller;

import com.farihim.assessment.demo.dto.request.UserRequest;
import com.farihim.assessment.demo.dto.response.UserResponse;
import com.farihim.assessment.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/paged")
    public Page<UserResponse> getAllUsersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return userService.getAllUsersPaged(page, size, sortBy, sortDir);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}