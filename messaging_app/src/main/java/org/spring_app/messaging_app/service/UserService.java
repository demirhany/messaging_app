package org.spring_app.messaging_app.service;

import lombok.AllArgsConstructor;
import org.spring_app.messaging_app.dto.user.UserGetRequest;
import org.spring_app.messaging_app.entity.User;
import org.spring_app.messaging_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserGetRequest> getAllUsers() {
        return userRepository.findAll().stream().map(UserGetRequest::new).toList();
    }

    public User getUserByNick(String nick) {
        return userRepository.findByNick(nick);
    }
}
