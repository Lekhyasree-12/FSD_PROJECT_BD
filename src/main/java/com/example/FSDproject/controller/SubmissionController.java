package com.example.FSDproject.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.FSDproject.model.Submission;
import com.example.FSDproject.service.SubmissionService;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService service;

    // 🔥 GET ALL
    @GetMapping
    public List<Submission> getAll() {
        return service.getAll();
    }

    // 🔥 CREATE (UPLOAD)
    @PostMapping
    public Submission uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("studentId") Long studentId,
            @RequestParam("assignmentId") Long assignmentId) throws IOException {

        String uploadDir = "/tmp/uploads/";
        Files.createDirectories(Paths.get(uploadDir));
        Path filePath = Paths.get(uploadDir + file.getOriginalFilename());
        Files.write(filePath, file.getBytes());

        Submission s = new Submission();
        s.setStudentId(studentId);
        s.setAssignmentId(assignmentId);
        s.setFileName(file.getOriginalFilename());
        s.setFilePath(filePath.toString());

        return service.save(s);
    }

    // 🔥 DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // 🔥 UPDATE (RE-UPLOAD FILE)
    @PutMapping("/{id}")
    public Submission updateFile(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        Submission s = service.getById(id);
        if (s == null) throw new RuntimeException("Submission not found");

        String uploadDir = "/tmp/uploads/";
        Files.createDirectories(Paths.get(uploadDir));
        Path filePath = Paths.get(uploadDir + file.getOriginalFilename());
        Files.write(filePath, file.getBytes());

        s.setFileName(file.getOriginalFilename());
        s.setFilePath(filePath.toString());

        return service.save(s);
    }

    // 🔥 GET BY ID
    @GetMapping("/{id}")
    public Submission getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // 🔥 GRADE
    @PutMapping("/{id}/grade")
    public Submission gradeSubmission(
            @PathVariable Long id,
            @RequestBody Submission updated) {

        Submission s = service.getById(id);

        if (s == null) {
            throw new RuntimeException("Submission not found");
        }

        s.setScore(updated.getScore());
        s.setFeedback(updated.getFeedback());

        return service.save(s);
    }
}