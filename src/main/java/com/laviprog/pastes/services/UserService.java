package com.laviprog.pastes.services;

import com.laviprog.pastes.dto.user.CreateUserRequest;
import com.laviprog.pastes.dto.user.UpdateUserRequest;
import com.laviprog.pastes.exceptions.CustomAccessDeniedException;
import com.laviprog.pastes.exceptions.ResourceNotFoundException;
import com.laviprog.pastes.exceptions.UserAlreadyExistsException;
import com.laviprog.pastes.models.User;
import com.laviprog.pastes.models.enums.Role;
import com.laviprog.pastes.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ViewService viewService;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getByUsername(String username) throws ResourceNotFoundException {
        return repository
                .findByUsername(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("The user was not found")
                );
    }

    public User create(CreateUserRequest createUserRequest) throws UserAlreadyExistsException {
        return create(
                User.builder()
                        .email(createUserRequest.getEmail())
                        .password(passwordEncoder.encode(createUserRequest.getPassword()))
                        .username(createUserRequest.getUsername())
                        .role(createUserRequest.getRole())
                        .build()
        );
    }

    public User create(User user) throws UserAlreadyExistsException {
        Optional<User> existingUser = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        checkUsernameAndEmail(existingUser, user.getUsername(), user.getEmail());

        return save(user);
    }

    private User save(User user) {
        return repository.save(user);
    }

    public User getById(Long id) throws ResourceNotFoundException {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("The user was not found")
                );
    }

    @Transactional
    public void deleteById(Long id, User authenticatedUser) throws ResourceNotFoundException, CustomAccessDeniedException {

        User user = getById(id);

        if (authenticatedUser.getId().equals(id) || authenticatedUser.getRole() == Role.ADMIN) {

            repository.delete(user);
            viewService.deleteAllViewsByUserId(id);

        } else {
            throw new CustomAccessDeniedException("You do not have permission to delete this object");
        }
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User update(UpdateUserRequest userRequest, User authenticatedUser) {

        Optional<User> existingUser = repository.findByUsernameOrEmailExceptId(
                userRequest.getUsername(),
                userRequest.getEmail(),
                userRequest.getId()
        );

        checkUsernameAndEmail(existingUser, userRequest.getUsername(), userRequest.getEmail());

        if (authenticatedUser.getId().equals(userRequest.getId()) || authenticatedUser.getRole() == Role.ADMIN) {
            User user = getById(userRequest.getId());

            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setUsername(userRequest.getUsername());

            return save(user);
        } else {
            throw new CustomAccessDeniedException("You do not have permission to update this object");
        }
    }

    public User updateRole(Long id, Role role) throws ResourceNotFoundException {
        User user = getById(id);

        user.setRole(role);

        return save(user);
    }

    private void checkUsernameAndEmail(Optional<User> existingUser, String username, String email) throws UserAlreadyExistsException {
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(
                    existingUser.get().getUsername().equals(username)
                            ? "User with username '" + username + "' already exists"
                            : "User with email '" + email + "' already exists"
            );
        }
    }
}
