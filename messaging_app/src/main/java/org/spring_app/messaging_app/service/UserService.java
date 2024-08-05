package org.spring_app.messaging_app.service;

import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.dto.UserDto;
import org.spring_app.messaging_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::new).toList();
    }
}
