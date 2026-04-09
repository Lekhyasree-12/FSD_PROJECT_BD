package com.example.FSDproject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.FSDproject.model.Assignment;
import com.example.FSDproject.service.AssignmentService;


@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService service;

    @GetMapping
    public List<Assignment> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Assignment add(@RequestBody Assignment a) {
        return service.save(a);
    }
}