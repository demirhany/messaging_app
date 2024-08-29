package org.spring_app.messaging_app.controller.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class WebController {
    @GetMapping("/index")
    public String index() {
        return "/websocket/index";
    }
}
