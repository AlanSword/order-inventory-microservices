package com.example.user_management.dto;

import java.util.Set;

public class UserView {
    public Integer id;
    public String username;
    public boolean enabled;
    public Set<String> roles;
}
