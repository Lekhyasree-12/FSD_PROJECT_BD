package com.example.FSDproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FSDproject.model.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}