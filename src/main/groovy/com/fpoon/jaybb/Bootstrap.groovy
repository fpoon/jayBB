package com.fpoon.jaybb

import com.fpoon.jaybb.domain.Forum
import com.fpoon.jaybb.domain.Message
import com.fpoon.jaybb.domain.Thread
import com.fpoon.jaybb.repository.ForumRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Bootstrap implements InitializingBean {

    static MAX_MESSAGES = 20;

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
        createThreads(forum)
        return forum
    }

    def createThreads(Forum forum) {
        (1..5).each {
            def thread = new Thread();
            thread.title = "Test thread ${it}"
            (1..MAX_MESSAGES).each {
                Message msg = new Message();
                msg.setTitle("RE: Test message ${it}")
                msg.setContent("Hello! I'm Test message no. ${it}")
                thread.messages.add(msg)
            }
            forum.threads.add(thread)
        }
    }
}
