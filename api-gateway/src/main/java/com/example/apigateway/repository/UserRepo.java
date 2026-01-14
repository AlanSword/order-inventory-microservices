package com.example.apigateway.repository;

import com.example.apigateway.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    @Query("""
    SELECT DISTINCT u
    FROM Users u
    LEFT JOIN FETCH u.roles r
    WHERE u.username = :username
      AND u.enabled = true
""")
    Optional<Users> findByUsernameWithRoles(@Param("username") String username);
}