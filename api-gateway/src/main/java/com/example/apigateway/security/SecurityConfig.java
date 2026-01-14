package com.example.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.apigateway.repository.UserRepo;
import com.example.apigateway.model.Users;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/inventory/**", "/api/orders/**", "/admin/**")
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/inventory/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/inventory/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/orders/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    // UserDetailsService (blocking OK for MVC)
    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo) {
        return username -> {
            // Fetch user with roles (your JOIN query)
            Users user = userRepo.findByUsernameWithRoles(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            // Extract authorities from roles
            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                    .collect(Collectors.toList());

            // Spring Security UserDetails
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .accountExpired(false)
                    .credentialsExpired(false)
                    .accountLocked(false)
                    .disabled(!user.isEnabled())
                    .build();
        };
    }
}
