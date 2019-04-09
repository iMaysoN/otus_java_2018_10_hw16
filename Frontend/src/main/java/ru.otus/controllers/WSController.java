package ru.otus.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.service.FrontendService;
import ru.otus.domain.WSMsg;

@Controller
public class WSController {

    private final FrontendService frontendService;

    public WSController(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @MessageMapping({"/connect", "/message"})
    @SendTo("/topic/response")
    public void getUsers() {
        frontendService.getUsers();
    }

    @MessageMapping("/save")
    @SendTo("/topic/response")
    public void saveUser(WSMsg wsMsg) {
        if (wsMsg.getUser() != null) {
            frontendService.saveUser(wsMsg.getUser());
        }
    }

    @MessageMapping("/delete")
    @SendTo("/topic/response")
    public void deleteUser(WSMsg wsMsg) {
        if (wsMsg.getUser() != null) {
            frontendService.deleteUser(wsMsg.getUser());
        }
    }
}
