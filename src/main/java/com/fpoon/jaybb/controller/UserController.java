package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.articlebot.service.UserService;
import com.fpoon.jaybb.dto.CreateUserDTO;
import com.fpoon.jaybb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signIn() {
        return "signin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("dto", new CreateUserDTO());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(CreateUserDTO dto) {
        userService.createUser(dto);
        return "redirect:/signin";
    }
}
