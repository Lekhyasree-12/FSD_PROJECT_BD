package com.example.FSDproject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.FSDproject.model.Subject;
import com.example.FSDproject.repository.SubjectRepository;

@RestController
@RequestMapping("/subjects")
@CrossOrigin(origins = "*")
public class SubjectController {

    @Autowired
    private SubjectRepository repo;

    @GetMapping
    public List<Subject> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Subject add(@RequestBody Subject subject) {
        return repo.save(subject);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}