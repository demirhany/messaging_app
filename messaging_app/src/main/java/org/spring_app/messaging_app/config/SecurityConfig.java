package org.spring_app.messaging_app.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.filter.JwtAuthenticationFilter;
import org.spring_app.messaging_app.repository.UserRepository;
import org.spring_app.messaging_app.service.JwtService;
import org.spring_app.messaging_app.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(
                        username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   UserDetailsService userDetailsService,
                                                   AuthenticationProvider authenticationProvider) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/authentication/login", "/api/authentication/register").permitAll()
                        .requestMatchers("/auth/register", "/auth/login").permitAll()

                        .requestMatchers("/").permitAll()

                        //static
                        .requestMatchers(HttpMethod.GET, "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider);
        http
                .logout((logout) ->
                        logout
                                .logoutSuccessUrl("/auth/login")
                                .logoutUrl("/auth/logout")
                                .deleteCookies("jwtToken", "JSESSIONID")
                );
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> {
                            if (request.getHeader("Authorization") == null) {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                            } else {
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, authException.getMessage());
                            }
                        }
                ));
        JwtAuthenticationFilter jwtSecurityFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        http.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
