package com.fpoon.jaybb

import com.fpoon.jaybb.articlebot.service.UserService
import com.fpoon.jaybb.constant.UserRoles
import com.fpoon.jaybb.domain.Forum
import com.fpoon.jaybb.domain.Message
import com.fpoon.jaybb.domain.Thread
import com.fpoon.jaybb.domain.User
import com.fpoon.jaybb.dto.CreateUserDTO
import com.fpoon.jaybb.repository.ForumRepository
import com.fpoon.jaybb.repository.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class Bootstrap implements InitializingBean {

    static MAX_MESSAGES = 20;

    @Autowired
    private final ForumRepository forumRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserService userService

    @Autowired
    private final PasswordEncoder passwordEncoder

    def forums = ["News", "JayBB Discussions", "JayBB Help", "Off-Topic Discussions", "Other"]

    @Override
    void afterPropertiesSet() throws Exception {
        createUsers()
        createForums()
    }

    def createForums() {
        forums.each {forumRepository.save(createForum(it, 3))}
    }

    def createUsers() {

        User admin = userService.createUser(new CreateUserDTO("admin", "admin@admin", "password"))
        admin.getRoles().addAll([UserRoles.ADMIN, UserRoles.MODERATOR, UserRoles.USER]);
        userRepository.save(admin)

        User moderator = userService.createUser(new CreateUserDTO("moderator", "moderator@moderator", "password"))
        moderator.getRoles().addAll([UserRoles.MODERATOR, UserRoles.USER]);
        userRepository.save(moderator)

        User user = userService.createUser(new CreateUserDTO("user", "user@user", "password"))
        user.getRoles().addAll([UserRoles.USER]);
        userRepository.save(user)
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
