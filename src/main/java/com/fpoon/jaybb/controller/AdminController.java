package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.constant.UserRoles;
import com.fpoon.jaybb.domain.User;
import com.fpoon.jaybb.service.UserService;
import com.fpoon.jaybb.wrapper.PageWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    @Secured(UserRoles.ADMIN)
    public String adminPanel() {
        return "admin/admin";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @Secured(UserRoles.ADMIN)
    public String adminPanel(Pageable pageable, Model model) {
        PageWrapper<User> users = new PageWrapper<>(userService.getUsers(pageable));
        model.addAttribute("users", users);
        return "admin/users";
    }

}
