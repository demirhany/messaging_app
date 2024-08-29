package org.spring_app.messaging_app.service;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.spring_app.messaging_app.dto.AuthenticationRequest;
import org.spring_app.messaging_app.dto.UserDto;
import org.spring_app.messaging_app.dto.UserGetRequest;
import org.spring_app.messaging_app.entity.User;
import org.spring_app.messaging_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserGetRequest> getAllUsers() {
        return userRepository.findAll().stream().map(UserGetRequest::new).toList();
    }

    public Optional<UserDto> getUserByNick(String nick) {
        return userRepository.findByNick(nick).map(UserDto::new);
    }
}
