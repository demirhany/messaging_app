package org.spring_app.messaging_app.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.dto.*;
import org.spring_app.messaging_app.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authentication")
public class AuthenticationRestController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse httpServletResponse) {
        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);
        httpServletResponse.addCookie(response.getCookie());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @GetMapping("/isAuth")
    public ResponseEntity<String> isAuth() {
        return ResponseEntity.ok("");
    }
}
