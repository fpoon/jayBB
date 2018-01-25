package com.fpoon.jaybb.controller;

import com.fpoon.jaybb.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editMessage(@PathVariable Long id, @RequestParam(name = "message", required = true) String message, @RequestHeader(name = "referer") String referrer) {
        messageService.editMessage(id, message);
        return ResponseEntity.ok(referrer);
    }
}
