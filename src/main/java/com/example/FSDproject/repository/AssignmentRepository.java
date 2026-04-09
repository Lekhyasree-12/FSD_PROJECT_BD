package com.example.FSDproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FSDproject.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}