package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.domain.Forum;
import com.fpoon.jaybb.domain.Thread;
import com.fpoon.jaybb.dto.ThreadDTO;
import com.fpoon.jaybb.repository.ForumRepository;
import com.fpoon.jaybb.repository.ThreadRepository;
import com.fpoon.jaybb.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/forum")
@RequiredArgsConstructor
public class ForumController {
    private final ForumRepository forumRepository;
    private final ThreadRepository threadRepository;

    private final ThreadService threadService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Transactional
    public String getForum(@PathVariable Long id,
                           @SortDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                           Model model) {
        Forum forum = forumRepository.findOne(id);
        Page<Thread> threads = threadService.listThreads(forum.getId(), pageable);
        model.addAttribute("forum", forum);
        model.addAttribute("threads",threads);
        return "forum";
    }

    @RequestMapping(value = "/{id}/thread", method = RequestMethod.POST)
    @ResponseBody
    public Long postThread(@PathVariable Long id,
                           ThreadDTO dto) {
        Forum forum = forumRepository.findOne(id);
        Thread th = threadService.addThreadToForum(dto, forum);
        return th.getId();
    }
}
