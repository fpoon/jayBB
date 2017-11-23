package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.domain.Message;
import com.fpoon.jaybb.domain.Thread;
import com.fpoon.jaybb.repository.MessageRepository;
import com.fpoon.jaybb.repository.ThreadRepository;
import com.fpoon.jaybb.wrapper.PageWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ThreadController {
    private final ThreadRepository threadRepository;
    private final MessageRepository messageRepository;

    @RequestMapping(value = "/thread/{id}")
    public String getThread(@PathVariable Long id, Pageable pageable, Model model) {
        Thread thread = threadRepository.findOne(id);
        Page<Message> messages = messageRepository.findAllByThreadId(thread.getId(), pageable);

        model.addAttribute("thread", thread);
        model.addAttribute("messages", new PageWrapper<>(messages));

        return "thread";
    }
}
