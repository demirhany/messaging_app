package org.spring_app.messaging_app.service;

import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.dto.AuthenticationRequest;
import org.spring_app.messaging_app.dto.AuthenticationResponse;
import org.spring_app.messaging_app.dto.UserCreateRequest;
import org.spring_app.messaging_app.dto.UserDto;
import org.spring_app.messaging_app.entity.Role;
import org.spring_app.messaging_app.entity.User;
import org.spring_app.messaging_app.repository.RoleRepository;
import org.spring_app.messaging_app.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public UserDto register(UserCreateRequest request) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findRoleByRoleName("USER"));
        var user = User.builder()
                .email(request.getEmail())
                .nick(request.getNick())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        return new UserDto(userRepository.save(user));
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user.getUsername());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public UserDto findUserByAuth(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new UserDto();
        } else {
            String jwt = authHeader.substring(7);
            String username = jwtService.extractUsername(jwt);
            User user = userRepository.findByEmail(username).orElseThrow();
            return new UserDto(user);
        }
    }
}
