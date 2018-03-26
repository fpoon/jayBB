package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.constant.UserRoles;
import com.fpoon.jaybb.domain.Forum;
import com.fpoon.jaybb.domain.Thread;
import com.fpoon.jaybb.domain.User;
import com.fpoon.jaybb.dto.BotThreadDTO;
import com.fpoon.jaybb.dto.ThreadDTO;
import com.fpoon.jaybb.repository.ForumRepository;
import com.fpoon.jaybb.repository.ThreadRepository;
import com.fpoon.jaybb.service.ForumService;
import com.fpoon.jaybb.service.ThreadService;
import com.fpoon.jaybb.service.UserService;
import com.fpoon.jaybb.wrapper.PageWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/forum")
@RequiredArgsConstructor
public class ForumController {
    private final ForumRepository forumRepository;
    private final ThreadRepository threadRepository;

    private final ThreadService threadService;
    private final ForumService forumService;
    private final UserService userService;

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

    @RequestMapping(value = "/{id}/moderators", method = RequestMethod.GET)
    @Transactional
    @Secured(UserRoles.ADMIN)
    public String getForumModerators(@PathVariable Long id,
                           Pageable pageable,
                           Model model) {
        Forum forum = forumRepository.findOne(id);
        Page<User> users = userService.getUsersWithRole(UserRoles.MODERATOR, pageable);
        model.addAttribute("forum", forum);
        model.addAttribute("users", new PageWrapper<>(users));
        return "forumMods";
    }

    @RequestMapping(value = "/{id}/moderators", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<?> postForumModerators(@PathVariable Long id,
                                                 @RequestParam(name = "userId") Long userId,
                                                 @RequestHeader(name = "referer", required = false) String referrer) { ;
        forumService.addModerator(id, userId);
        return ResponseEntity.ok(referrer);
    }

    @RequestMapping(value = "/{forumId}/moderators/{userId}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<?> deleteForumModerators(@PathVariable Long forumId, @PathVariable Long userId,
                                        @RequestHeader(name = "referer", required = false) String referrer) {
        forumService.removeModerator(forumId, userId);
        return ResponseEntity.ok(referrer);
    }

    @RequestMapping(value = "/{id}/thread", method = RequestMethod.POST)
    @ResponseBody
    public Long postThread(@PathVariable Long id,
                           ThreadDTO dto) {
        Forum forum = forumRepository.findOne(id);
        Thread th = threadService.addThreadToForum(dto, forum);
        return th.getId();
    }

    @RequestMapping(value = "/{id}/thread/bot", method = RequestMethod.POST)
    @Secured({UserRoles.MODERATOR, UserRoles.ADMIN})
    @ResponseBody
    public Long postThreadWithBot(@PathVariable Long id,
                           @RequestBody BotThreadDTO dto) {
        Forum forum = forumRepository.findOne(id);
        Thread th = threadService.addThreadToForum(dto, forum);
        return th.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Secured(UserRoles.ADMIN)
    public ResponseEntity<?> deleteForum(@PathVariable Long id) {
        forumService.deleteForum(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @Secured({UserRoles.ADMIN, UserRoles.MODERATOR})
    public ResponseEntity<?> newForum(@RequestParam(name = "title", required = true) String title,
                                      @RequestParam(name = "parentId", required = false) Long parentId) {
        Forum forum = forumService.newForum(title, parentId);
        return ResponseEntity.ok("/forum/"+forum.getId());
    }
}
