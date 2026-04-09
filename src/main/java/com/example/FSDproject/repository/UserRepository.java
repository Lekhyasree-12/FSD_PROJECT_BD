package com.example.FSDproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FSDproject.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}