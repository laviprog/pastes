package com.laviprog.pastes.repositories;

import com.laviprog.pastes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query(value = "SELECT * FROM users WHERE user_id != :id AND (username = :username OR email = :email) LIMIT 1", nativeQuery = true)
    Optional<User> findByUsernameOrEmailExceptId(@Param("username") String username, @Param("email") String email, @Param("id") Long id);
}
