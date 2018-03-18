package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.constant.UserRoles;
import com.fpoon.jaybb.domain.Forum;
import com.fpoon.jaybb.domain.RssSource;
import com.fpoon.jaybb.repository.ForumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/forum")
@RequiredArgsConstructor
public class BotController {

    private final ForumRepository forumRepository;

    @RequestMapping(value = "/{id}/articles", method = RequestMethod.GET)
    @Transactional
    @Secured({UserRoles.ADMIN, UserRoles.MODERATOR})
    public String getArticles(@PathVariable Long id,
                                     Model model) {
        Forum forum = forumRepository.findOne(id);
        model.addAttribute("forum", forum);
        model.addAttribute("sources", forum.getRssSources());
        return "forumSources";
    }

    @RequestMapping(value = "/{id}/articles", method = RequestMethod.POST)
    @Transactional
    @Secured({UserRoles.ADMIN, UserRoles.MODERATOR})
    public String postArticles(@PathVariable Long id,
                               @RequestParam @NotBlank @URL String url) {
        Forum forum = forumRepository.findOne(id);
        forum.getRssSources().add(new RssSource(url));
        forumRepository.save(forum);
        return "redirect:/forum/"+id+"/articles";
    }

    @RequestMapping(value = "/{id}/articles/{linkId}", method = RequestMethod.DELETE)
    @Transactional
    @Secured({UserRoles.ADMIN, UserRoles.MODERATOR})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteArticles(@PathVariable Long id,
                               @PathVariable Long linkId) {
        Forum forum = forumRepository.findOne(id);
        val src = forum.getRssSources().stream().filter(r -> r.getId().equals(linkId)).findAny().orElse(null);
        forum.getRssSources().remove(src);
        forumRepository.save(forum);
    }
}
