package com.example.FSDproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FSDproject.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}