package com.fpoon.jaybb.service;

import com.fpoon.jaybb.domain.Message;
import com.fpoon.jaybb.domain.Thread;
import com.fpoon.jaybb.dto.MessageDTO;
import com.fpoon.jaybb.repository.MessageRepository;
import com.fpoon.jaybb.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ThreadRepository threadRepository;

    @Transactional
    public Thread addMessageToThread(MessageDTO dto, Thread thread) {
        Message msg = new Message(dto.getTitle(), dto.getContent());
        thread.getMessages().add(msg);
        thread.setMessagesSize(thread.getMessages().size());
        return threadRepository.save(thread);
    }

    @Transactional
    public Pageable getPageWithMessage(Message msg) {
        return null;
    }
}
