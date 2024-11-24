package com.laviprog.pastes.services;

import com.laviprog.pastes.exceptions.UserAlreadyExistsException;
import com.laviprog.pastes.models.User;
import com.laviprog.pastes.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user was not found"));
    }

    public User create(User user) {
        Optional<User> existingUser = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        log.info(existingUser.isPresent() ? existingUser.get().getUsername() : "The user already exists");
        if (existingUser.isPresent()) {
            if (existingUser.get().getUsername().equals(user.getUsername())) {
                throw new UserAlreadyExistsException("User with username '" + user.getUsername() + "' already exists");
            } else {
                throw new UserAlreadyExistsException("User with email '" + user.getEmail() + "' already exists");
            }
        }

        return save(user);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("The user was not found"));
    }
}
