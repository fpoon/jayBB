package com.fpoon.jaybb.service;

import com.fpoon.jaybb.domain.Forum;
import com.fpoon.jaybb.domain.Thread;
import com.fpoon.jaybb.dto.ThreadDTO;
import com.fpoon.jaybb.repository.ForumRepository;
import com.fpoon.jaybb.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThreadService {
    private final ForumRepository forumRepository;
    private final ThreadRepository threadRepository;

    @Transactional
    public Thread addThreadToForum(ThreadDTO dto, Forum forum) {
        Thread thread = new Thread(dto.getTitle(), dto.getContent());
        thread = threadRepository.save(thread);
        forum.getThreads().add(thread);
        forumRepository.save(forum);
        return thread;
    }
}
