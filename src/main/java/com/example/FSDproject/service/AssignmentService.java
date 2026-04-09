package com.example.FSDproject.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.FSDproject.model.Assignment;
import com.example.FSDproject.repository.AssignmentRepository;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository repo;

    public List<Assignment> getAll() {
        return repo.findAll();
    }

    public Assignment save(Assignment a) {
        return repo.save(a);
    }
}