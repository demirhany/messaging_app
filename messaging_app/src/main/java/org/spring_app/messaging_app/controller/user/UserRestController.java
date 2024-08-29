package org.spring_app.messaging_app.controller.user;

import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.dto.UserDto;
import org.spring_app.messaging_app.dto.UserGetRequest;
import org.spring_app.messaging_app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserGetRequest>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


}
