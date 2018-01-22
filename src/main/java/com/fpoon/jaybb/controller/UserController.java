package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.constant.UserRoles;
import com.fpoon.jaybb.domain.User;
import com.fpoon.jaybb.dto.CreateUserDTO;
import com.fpoon.jaybb.repository.UserRepository;
import com.fpoon.jaybb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestHeader(value = "referer", required = false) String referrer) {
        User user = userService.getCurrentUser();

        if (user.getRoles().contains(UserRoles.ADMIN) || id.equals(user.getId())) {
            userService.deleteUser(id);
        } else {
            throw new AuthorizationServiceException("You're unauthorized to access this resource");
        }

        if (user.getRoles().contains(UserRoles.ADMIN)) {
            return ResponseEntity.ok(referrer);
        } else {
            return ResponseEntity.ok("/");
        }
    }
}
