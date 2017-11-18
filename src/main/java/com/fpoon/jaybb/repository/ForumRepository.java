package com.fpoon.jaybb.repository;

import com.fpoon.jaybb.domain.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    List<Forum> findAllByRootTrue();
}
