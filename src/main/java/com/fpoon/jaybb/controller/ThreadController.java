package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.domain.Message;
import com.fpoon.jaybb.domain.Thread;
import com.fpoon.jaybb.dto.MessageDTO;
import com.fpoon.jaybb.repository.MessageRepository;
import com.fpoon.jaybb.repository.ThreadRepository;
import com.fpoon.jaybb.service.MessageService;
import com.fpoon.jaybb.wrapper.PageWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/thread")
@RequiredArgsConstructor
public class ThreadController {
    private final ThreadRepository threadRepository;
    private final MessageRepository messageRepository;

    private final MessageService messageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getThread(@PathVariable Long id, Pageable pageable, Model model) {
        Thread thread = threadRepository.findOne(id);
        Page<Message> messages = messageRepository.findAllByThreadId(thread.getId(), pageable);

        model.addAttribute("thread", thread);
        model.addAttribute("messages", new PageWrapper<>(messages));

        return "thread";
    }

    @ResponseBody
    @RequestMapping(value ="/{id}/message", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public Integer postMessage(@PathVariable Long id,
                              MessageDTO dto,
                              Pageable pageable) {
        Thread thread = threadRepository.findOne(id);
        messageService.addMessageToThread(dto, thread);
        return messageRepository.findAllByThreadId(id, pageable).getTotalPages()-1;
    }
}
