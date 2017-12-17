package com.fpoon.jaybb.config;

import com.fpoon.jaybb.articlebot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
@Slf4j
public class JpaConfig {

    private final UserService userService;

    @Value("${jaybb.default.user.username:Guest}")
    private String defaultUsername;

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            String username = null;
            try {
                username = SecurityContextHolder.getContext().getAuthentication().getName();
            } catch (NullPointerException e) {
                log.warn("Unknown user. Falling back to default username: {}", defaultUsername);
                username = defaultUsername;
            }
            return username;
        };
    }
}
