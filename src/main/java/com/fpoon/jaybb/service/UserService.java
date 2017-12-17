package com.fpoon.jaybb.service;

import com.fpoon.jaybb.constant.UserRoles;
import com.fpoon.jaybb.domain.User;
import com.fpoon.jaybb.dto.CreateUserDTO;
import com.fpoon.jaybb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jaybb.default.user.username:Guest}")
    private String defaultUsername;

    @Value("${jaybb.default.user.email:guest@jaybb}")
    private String defaultEmail;

    @PostConstruct
    public void createDefaultUser() {
        User defUser = userRepository.findTopByUsername(defaultUsername).orElseGet(() -> {
            log.info("Creating default user...");
            User user = new User();
            user.setUsername(defaultUsername);
            user.setEmail(defaultEmail);
            return userRepository.save(user);
        });

        log.info("Default user: Username: {} | Email: {}", defUser.getEmail(), defUser.getEmail());
    }

    @Transactional
    public User createUser(CreateUserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.getRoles().add(UserRoles.USER);
        return userRepository.save(user);
    }

    @Transactional
    public User getCurrentUser() {
        String login = null;

        try {
            login = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            log.error("Cannot get current user. Exception: {}", e);
        }

        if (login == null)
            return null;

        return userRepository.findTopByUsername(login).orElse(null);
    }

    @Transactional
    protected User getDefaultUser() {
        return userRepository.findTopByUsername(defaultUsername).orElse(null);
    }
}
