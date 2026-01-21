package com.example.user_management.controller;


import com.example.user_management.dto.RegisterUserRequest;
import com.example.user_management.dto.UpdatePasswordRequest;
import com.example.user_management.dto.UserView;
import com.example.user_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public String me(@RequestHeader("X-Authenticated-User") String username) {
        return username;
    }
    @PostMapping
    public ResponseEntity<UserView> register(@Valid @RequestBody RegisterUserRequest req) {
        UserView created = userService.register(req);
        URI location = URI.create("/api/users/" + created.id);
        return ResponseEntity.created(location).body(created);
    }


    @GetMapping("/admin/userList")
    public ResponseEntity<List<UserView>> list() {
        return ResponseEntity.ok(userService.findAllUsersWithRoles());
    }


    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteByAdmin(@PathVariable long id) {
        userService.deleteUserByID(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete( @RequestHeader("X-Authenticated-User") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/{id}/resetpassword")
    public ResponseEntity<Void> updatePasswordByAdmin(@PathVariable Long id,
                                               @Valid @RequestBody UpdatePasswordRequest req) {
        userService.updatePassword(id, req);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/resetpassword")
    public ResponseEntity<Void> updatePassword( @RequestHeader("X-Authenticated-User") String username,
                                               @Valid @RequestBody UpdatePasswordRequest req) {
        userService.updatePassword(username, req);
        return ResponseEntity.ok().build();
    }
}
