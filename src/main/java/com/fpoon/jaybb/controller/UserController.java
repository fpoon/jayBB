package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signIn() {
        return "signin";
    }
}
