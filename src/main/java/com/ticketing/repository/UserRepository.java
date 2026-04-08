package com.ticketing.repository;

import com.ticketing.Enums.Role;
import com.ticketing.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String username, String name, String email, Pageable pageable);

    Page<User> findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndRole(
            String username, String name, String email, Role role, Pageable pageable);

    Page<User> findByRole(Role role, Pageable pageable);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}