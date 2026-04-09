package com.example.FSDproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FSDproject.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}