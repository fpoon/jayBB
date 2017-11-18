package com.fpoon.jaybb.repository;

import com.fpoon.jaybb.domain.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
}
