package com.fpoon.jaybb.service

import com.fpoon.jaybb.domain.Message
import com.fpoon.jaybb.domain.Thread
import com.fpoon.jaybb.domain.User
import com.fpoon.jaybb.dto.MessageDTO
import com.fpoon.jaybb.repository.MessageRepository
import com.fpoon.jaybb.repository.ThreadRepository
import org.springframework.data.domain.Pageable
import service.HtmlPurifier
import service.MessageService
import service.UserService
import spock.lang.Specification

class MessageServiceTest extends Specification {

    private MessageRepository messageRepository
    private ThreadRepository threadRepository
    private UserService userService
    private HtmlPurifier htmlPurifier
    private MessageService messageService

    def setup() {
        messageRepository = Mock(MessageRepository.class)
        threadRepository = Mock(ThreadRepository.class)
        userService = Mock(UserService.class)
        htmlPurifier = Mock(HtmlPurifier.class)

        messageService = new MessageService(htmlPurifier, userService, messageRepository, threadRepository)
    }

    def "test add Message To Thread"() {
        given:
        def thTitle = "thTitle"
        def thContent = "thContent"
        Thread thread = new Thread(thTitle, thContent)
        User fakeUser = new User()
        MessageDTO msg = new MessageDTO()
        msg.title = 'foo'
        msg.content = 'bar'

        and: "mock repository"
        threadRepository.save(_) >> thread
        userService.getCurrentUser() >> fakeUser
        htmlPurifier.purify(_) >> {it[0]}

        when:
        Thread result = messageService.addMessageToThread(msg, thread)

        then:
        result.messages.size() == result.messagesSize
        result.messages.any { it.title == msg.title && it.content == msg.content }
        result.messages.any { it.title == thTitle && it.content == thContent }
    }

    def "test get Page With Message"() {
        when:
        Pageable result = messageService.getPageWithMessage(new Message("title", "content"))

        then:
        result == null
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme