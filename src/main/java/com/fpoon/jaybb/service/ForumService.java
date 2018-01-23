package com.fpoon.jaybb.service;

import com.fpoon.jaybb.constant.UserRoles;
import com.fpoon.jaybb.domain.Forum;
import com.fpoon.jaybb.domain.User;
import com.fpoon.jaybb.repository.ForumRepository;
import com.fpoon.jaybb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ForumService {
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public void addModerator(Long forumId, Long userId) {
        Forum forum = forumRepository.findOne(forumId);
        User user = userRepository.findOne(userId);

        if (!user.getRoles().contains(UserRoles.MODERATOR))
            return;

        forum.getModerators().add(user);
        forumRepository.save(forum);
    }

    @Transactional
    public void removeModerator(Long forumId, Long userId) {
        Forum forum = forumRepository.findOne(forumId);
        User user = userRepository.findOne(userId);

        if (!user.getRoles().contains(UserRoles.MODERATOR))
            return;

        forum.getModerators().remove(user);
        forumRepository.save(forum);
    }
}
