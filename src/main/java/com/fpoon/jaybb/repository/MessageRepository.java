package com.fpoon.jaybb.repository;

import com.fpoon.jaybb.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
