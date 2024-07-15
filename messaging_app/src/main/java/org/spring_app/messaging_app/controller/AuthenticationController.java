package org.spring_app.messaging_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/httponly")
    public String httponly() {
        return "auth/httponly";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/auth/login";
    }
}
