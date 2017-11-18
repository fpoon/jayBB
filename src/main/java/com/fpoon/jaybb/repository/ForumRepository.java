package com.fpoon.jaybb.repository;

import com.fpoon.jaybb.domain.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
