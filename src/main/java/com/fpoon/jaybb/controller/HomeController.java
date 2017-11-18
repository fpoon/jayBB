package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.domain.Forum;
import com.fpoon.jaybb.repository.ForumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ForumRepository forumRepository;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    @Transactional
    public String home(Model model) {
        List<Forum> forums = forumRepository.findAllByRootTrue();
        model.addAttribute("forums", forums);
        return "index";
    }
}
