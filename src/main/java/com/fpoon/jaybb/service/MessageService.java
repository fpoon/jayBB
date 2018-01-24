package com.fpoon.jaybb.service;

import com.fpoon.jaybb.constant.Messages;
import com.fpoon.jaybb.domain.Message;
import com.fpoon.jaybb.domain.Thread;
import com.fpoon.jaybb.dto.MessageDTO;
import com.fpoon.jaybb.repository.MessageRepository;
import com.fpoon.jaybb.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final HtmlPurifier htmlPurifier;

    private final UserService userService;

    private final MessageRepository messageRepository;
    private final ThreadRepository threadRepository;

    @Transactional
    public Thread addMessageToThread(MessageDTO dto, Thread thread) {
        Message msg = new Message(dto.getTitle(), htmlPurifier.purify(dto.getContent()));
        msg.setUser(userService.getCurrentUser());
        thread.getMessages().add(msg);
        thread.setMessagesSize(thread.getMessages().size());
        return threadRepository.save(thread);
    }

    @Transactional
    public void removeMessage(Long id) {
        Message message = messageRepository.getOne(id);
        if (message.getUser() != userService.getCurrentUser() && !message.getThread().getForum().isModerator()) {
            throw new AuthorizationServiceException("You're unauthorized to access this resource");
        }
        message.setContent(Messages.MESSAGE_REMOVED);
        message.setRemoved(true);
        messageRepository.save(message);
    }

    @Transactional
    public Pageable getPageWithMessage(Message msg) {
        return null;
    }
}
