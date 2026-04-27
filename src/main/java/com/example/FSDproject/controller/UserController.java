package com.example.FSDproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.FSDproject.model.User;
import com.example.FSDproject.repository.UserRepository;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*") // 🔥 allow frontend access
public class UserController {

    @Autowired
    private UserRepository repo;

    // 🔥 REGISTER USER
    @PostMapping("/register")
    public User register(@RequestBody User user) {

        // Validate input
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            throw new RuntimeException("Invalid user data");
        }

        // Optional: prevent duplicate emails
        User existing = repo.findByEmail(user.getEmail());
        if (existing != null) {
            throw new RuntimeException("Email already exists");
        }

        return repo.save(user);
    }

    // 🔥 LOGIN USER
    @PostMapping("/login")
    public User login(@RequestBody User user) {

        // 🔒 Validate request
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            return null;
        }

        // 🔍 Find user
        User existing = repo.findByEmail(user.getEmail());

        // ❌ User not found
        if (existing == null) {
            return null;
        }

        // ❌ Password mismatch
        if (!existing.getPassword().equals(user.getPassword())) {
            return null;
        }

        // ✅ Success
        return existing;
    }
}