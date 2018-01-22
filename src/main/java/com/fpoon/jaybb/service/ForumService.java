package com.fpoon.jaybb.service;

import com.fpoon.jaybb.domain.Forum;
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

    @Transactional
    public Forum newForum(String title, Long parentId) {
        Forum forum = new Forum();
        forum.setTitle(title);

        if (parentId == null) {
            forum.setRoot(true);
            return forumRepository.save(forum);
        } else {
            Forum parent = forumRepository.findOne(parentId);
            forum = forumRepository.save(forum);
            parent.getForums().add(forum);
            forumRepository.save(parent);
        }

        return forum;
    }
}
