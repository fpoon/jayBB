package com.fpoon.jaybb.repository;

import com.fpoon.jaybb.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findTopByUsername(String username);

    @Query("select u from User u where :role member of u.roles")
    Page<User> findAllByRole(@Param("role") String role, Pageable pageable);
}
