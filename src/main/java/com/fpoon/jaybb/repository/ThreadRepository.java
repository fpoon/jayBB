package com.fpoon.jaybb.repository;

import com.fpoon.jaybb.domain.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
    Page<Thread> findAllByDeletedFalseAndForum_idOrderByLastModifiedDateDesc(Long forumId, Pageable pageable);
    Page<Thread> findAllByDeletedFalseAndForum_id(Long forumId, Pageable pageable);
}
