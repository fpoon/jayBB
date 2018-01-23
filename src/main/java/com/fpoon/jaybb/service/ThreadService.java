package com.fpoon.jaybb.service;

import com.fpoon.jaybb.domain.Forum;
import com.fpoon.jaybb.domain.Thread;
import com.fpoon.jaybb.dto.ThreadDTO;
import com.fpoon.jaybb.repository.ForumRepository;
import com.fpoon.jaybb.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThreadService {
    private final HtmlPurifier purifier;
    private final ForumRepository forumRepository;
    private final ThreadRepository threadRepository;

    public Page<Thread> listThreads(Long forumId, Pageable pageable) {
        PageRequest request = new PageRequest(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                new Sort(Sort.Direction.DESC, "sticky").and(pageable.getSort())
        );

        return threadRepository.findAllByDeletedFalseAndForum_id(forumId, request);
    }

    @Transactional
    public Thread addThreadToForum(ThreadDTO dto, Forum forum) {
        Thread thread = new Thread(dto.getTitle(), purifier.purify(dto.getContent()));
        thread = threadRepository.save(thread);
        forum.getThreads().add(thread);
        forumRepository.save(forum);
        return thread;
    }

    @Transactional
    @Modifying
    public void removeThread(Long id) {
        threadRepository.delete(id);
    }

    @Transactional
    public void stickThread(Long id) {
        Thread thread = threadRepository.findOne(id);
        thread.setSticky(!thread.isSticky());
        threadRepository.save(thread);
    }
}
