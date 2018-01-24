package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.constant.UserRoles;
import com.fpoon.jaybb.domain.User;
import com.fpoon.jaybb.dto.CreateUserDTO;
import com.fpoon.jaybb.repository.UserRepository;
import com.fpoon.jaybb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @RequestMapping(value = "/user/{id}/roles", method = RequestMethod.PUT)
    @Secured(UserRoles.ADMIN)
    public ResponseEntity<?> setRoles(@PathVariable Long id, @RequestParam(name = "role") String role, @RequestHeader(value = "referer", required = false) String referrer) {
        User user = userRepository.findOne(id);

        Set<String> roles = UserRoles.roles.get(role);

        if (roles != null) {
            user.getRoles().clear();
            user.getRoles().addAll(roles);
        }

        userRepository.save(user);

        return ResponseEntity.ok(referrer);
    }
}
