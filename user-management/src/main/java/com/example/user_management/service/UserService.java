package com.example.user_management.service;

import com.example.user_management.dto.RegisterUserRequest;
import com.example.user_management.dto.UpdatePasswordRequest;
import com.example.user_management.dto.UserView;
import com.example.user_management.entity.Role;
import com.example.user_management.entity.User;
import com.example.user_management.repo.RoleRepository;
import com.example.user_management.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserView register(RegisterUserRequest req) {
        User user = new User();
        user.setUsername(req.username);
        user.setPassword(passwordEncoder.encode(req.password)); // always encode [web:71]
        user.setEnabled(req.enabled);

        Set<Role> roles = req.roleNames.stream()
                .map(name -> roleRepo.findByName(name).orElseThrow(() -> new IllegalArgumentException("Role not found: " + name)))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        User saved = userRepo.save(user);

        return toView(saved);
    }

    @Transactional(readOnly = true)
    public List<UserView> findAllUsersWithRoles() {
        return userRepo.findAll().stream().map(this::toView).toList();
    }

    public void deleteUser(String username) {
        userRepo.deleteByUsername(username);
    }
    public void deleteUserByID(long id) {
        userRepo.deleteById(id);
    }

    public void updatePassword(Long id, UpdatePasswordRequest req) {
        User user = userRepo.findById(id).orElseThrow();

            if (passwordEncoder.matches(req.newPassword, user.getPassword())) {
                throw new IllegalArgumentException("Old password incorrect");
            }

        user.setPassword(passwordEncoder.encode(req.newPassword));
        userRepo.save(user);
    }
    public void updatePassword(String  username, UpdatePasswordRequest req) {
        User user = userRepo.findByUsername(username).orElseThrow();

        if (passwordEncoder.matches(req.newPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password incorrect");
        }

        user.setPassword(passwordEncoder.encode(req.newPassword));
        userRepo.save(user);
    }

    private UserView toView(User user) {
        UserView v = new UserView();
        v.id = user.getId();
        v.username = user.getUsername();
        v.enabled = user.isEnabled();
        v.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        return v;
    }
}
