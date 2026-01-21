package com.example.user_management.dto;

import java.util.Set;

public class RegisterUserRequest {
    public String username;
    public String password;
    public boolean enabled = true;
    public Set<String> roleNames;
}
