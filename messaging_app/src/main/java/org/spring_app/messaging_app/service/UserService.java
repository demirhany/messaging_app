package org.spring_app.messaging_app.service;

import lombok.AllArgsConstructor;
import org.spring_app.messaging_app.dto.UserDto;
import org.spring_app.messaging_app.entity.User;
import org.spring_app.messaging_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::new).toList();
    }

    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }
}
