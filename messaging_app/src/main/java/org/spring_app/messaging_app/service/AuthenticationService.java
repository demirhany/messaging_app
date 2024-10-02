package org.spring_app.messaging_app.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.controller.error.ErrorResponse;
import org.spring_app.messaging_app.controller.error.RestExceptionHandler;
import org.spring_app.messaging_app.dto.auth.AuthenticationRequest;
import org.spring_app.messaging_app.dto.auth.AuthenticationResponse;
import org.spring_app.messaging_app.dto.user.UserCreateRequest;
import org.spring_app.messaging_app.dto.user.UserDto;
import org.spring_app.messaging_app.entity.Role;
import org.spring_app.messaging_app.entity.User;
import org.spring_app.messaging_app.repository.RoleRepository;
import org.spring_app.messaging_app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public UserDto register(UserCreateRequest request) {
        try {
            if (request == null) {
                throw new IllegalArgumentException("Request cannot be null");
            }
            if (request.getEmail() == null || request.getNick() == null || request.getPassword() == null) {
                throw new NullPointerException("Email, Nick, or Password cannot be null");
            }
            List<Role> roles = new ArrayList<>();
            Role userRole = roleRepository.findRoleByRoleName("USER");
            if (userRole == null) {
                throw new NullPointerException("User role not found");
            }
            roles.add(userRole);
            var user = User.builder()
                    .email(request.getEmail())
                    .nick(request.getNick())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(roles)
                    .build();

            return new UserDto(userRepository.save(user));
        } catch (NullPointerException ex) {
            throw new RuntimeException("Null values encountered during user registration: " + ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid input provided: " + ex.getMessage(), ex);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws SQLException {
        try {
            if(request == null) {
                throw new IllegalArgumentException("Request cannot be null");
            }
            if(request.getEmail() == null || request.getPassword() == null) {
                throw new NullPointerException("Email or password cannot be null");
            }
            var user = userService.getUserByEmail(request.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var jwtToken = jwtService.generateToken(user.getUsername());

            Cookie cookie = new Cookie("jwtToken", jwtToken);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60 * 7);
            cookie.setAttribute("SameSite", "Lax");

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .cookie(cookie)
                    .nick(user.getNick())
                    .build();
        }  catch (NoSuchElementException ex) {
            throw new NoSuchElementException("No such element: " + ex.getMessage(), ex);
        }
    }

    public UserDto findUserByAuth(String authHeader) throws Exception {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new UserDto();
        } else {
            String jwt = authHeader.substring(7);
            String username = jwtService.extractUsername(jwt);
            User user = userService.getUserByEmail(username);
            return new UserDto(user);
        }
    }
}
