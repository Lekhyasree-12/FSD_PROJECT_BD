package com.example.FSDproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }
        User existing = repo.findByEmail(user.getEmail());
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        return ResponseEntity.ok(repo.save(user));
    }

    // 🔥 LOGIN USER
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        User existing = repo.findByEmail(user.getEmail());
        if (existing == null || !existing.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        return ResponseEntity.ok(existing);
    }
}