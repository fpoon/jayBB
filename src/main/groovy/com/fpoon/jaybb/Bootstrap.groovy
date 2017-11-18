package com.fpoon.jaybb

import com.fpoon.jaybb.domain.Forum
import com.fpoon.jaybb.repository.ForumRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Bootstrap implements InitializingBean {
    @Autowired
    private final ForumRepository forumRepository;

    def forums = ["News", "JayBB Discussions", "JayBB Help", "Off-Topic Discussions", "Other"]

    @Override
    void afterPropertiesSet() throws Exception {
        createForums()
    }

    def createForums() {
        forums.each {forumRepository.save(createForum(it, 3))}
    }

    def createForum(def title, def subforums) {
        def forum = new Forum()
        forum.setTitle(title)
        if (subforums) {
            (1..subforums).each {
                forum.forums += createForum("${title} - Subforum ${it}", null)
            }
            forum.root = true
        }
        return forum
    }
}
