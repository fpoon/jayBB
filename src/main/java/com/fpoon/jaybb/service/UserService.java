package com.fpoon.jaybb.service;

import com.fpoon.jaybb.constant.UserRoles;
import com.fpoon.jaybb.domain.User;
import com.fpoon.jaybb.dto.CreateUserDTO;
import com.fpoon.jaybb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(CreateUserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.getRoles().add(UserRoles.USER);
        return userRepository.save(user);
    }
}
