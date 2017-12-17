package com.fpoon.jaybb.articlebot.service;

import com.fpoon.jaybb.constant.UserRoles;
import com.fpoon.jaybb.domain.User;
import com.fpoon.jaybb.dto.CreateUserDTO;
import com.fpoon.jaybb.repository.UserRepository;
import com.google.common.base.Strings;
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
        fakeUser(defaultUsername, defaultEmail);
    }

    @Transactional
    public User fakeUser(String username, String email) {
        User fuser = userRepository.findTopByUsername(username).orElseGet(() -> {
            log.info("Creating fake user...");
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            return userRepository.save(user);
        });

        if (!Strings.isNullOrEmpty(fuser.getPassword())) {
            log.error("Username {} is taken by the real user!", fuser.getUsername());
            return null;
        }

        log.info("Fake user: Username: {} | Email: {}", fuser.getUsername(), fuser.getEmail());

        return fuser;
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
