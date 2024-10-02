package org.spring_app.messaging_app.service;

import lombok.AllArgsConstructor;
import org.spring_app.messaging_app.dto.user.UserGetRequest;
import org.spring_app.messaging_app.entity.User;
import org.spring_app.messaging_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserGetRequest> getAllUsers() {
        return userRepository.findAll().stream().map(UserGetRequest::new).toList();
    }

    public User getUserByNick(String nick) {
        return userRepository.findByNick(nick).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );
    }
}
