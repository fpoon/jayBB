package com.fpoon.jaybb.repository;

import com.fpoon.jaybb.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByThread_id(Long threadId, Pageable pageable);
}
