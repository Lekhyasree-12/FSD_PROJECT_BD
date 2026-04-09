package com.example.FSDproject.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.FSDproject.model.Submission;
import com.example.FSDproject.repository.SubmissionRepository;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository repo;

    public List<Submission> getAll() {
        return repo.findAll();
    }

    public Submission save(Submission s) {
        return repo.save(s);
    }
    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Submission getById(Long id) {
        return repo.findById(id).orElse(null);
    }
}