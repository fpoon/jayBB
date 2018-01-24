package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @RequestMapping(value =  "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeMessage(@PathVariable Long id, @RequestHeader(name = "referer") String referrer) {
        messageService.removeMessage(id);
        return ResponseEntity.ok(referrer);
    }
}
