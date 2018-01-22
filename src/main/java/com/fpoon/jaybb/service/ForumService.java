package com.fpoon.jaybb.service;

import com.fpoon.jaybb.repository.ForumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ForumService {
    private final ForumRepository forumRepository;

    @Transactional
    @Modifying
    public void deleteForum(Long id) {
        forumRepository.delete(id);
    }
}
