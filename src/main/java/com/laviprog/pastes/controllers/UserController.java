package com.laviprog.pastes.controllers;

import com.laviprog.pastes.dto.user.CreateUserRequest;
import com.laviprog.pastes.dto.user.UpdateUserRequest;
import com.laviprog.pastes.dto.user.UpdateUserRoleRequest;
import com.laviprog.pastes.dto.user.UserResponse;
import com.laviprog.pastes.models.User;
import com.laviprog.pastes.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAll().stream().map(UserResponse::new).toList();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return new UserResponse(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        userService.deleteById(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest userRequest) {
        return new UserResponse(userService.create(userRequest));
    }

    @PutMapping
    public UserResponse updateUser(@RequestBody @Valid UpdateUserRequest userRequest, @AuthenticationPrincipal User user) {
        return new UserResponse(userService.update(userRequest, user));
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse updateUserRole(@RequestBody @Valid UpdateUserRoleRequest userRequest) {
        return new UserResponse(userService.updateRole(userRequest.getId(), userRequest.getRole()));
    }
}
