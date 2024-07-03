package org.spring_app.messaging_app.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.dto.AuthenticationRequest;
import org.spring_app.messaging_app.dto.AuthenticationResponse;
import org.spring_app.messaging_app.dto.UserCreateRequest;
import org.spring_app.messaging_app.dto.UserDto;
import org.spring_app.messaging_app.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authentication")
public class AuthenticationRestController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse httpServletResponse) {
        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);
        Cookie cookie = new Cookie("jwtToken", response.getToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(1000 * 60 * 60 * 24 * 10);

        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
}
