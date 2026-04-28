package com.example.FSDproject.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.FSDproject.model.Submission;
import com.example.FSDproject.service.SubmissionService;

@RestController
@RequestMapping("/submissions")
@CrossOrigin(origins = "*")
public class SubmissionController {

    @Autowired
    private SubmissionService service;

    @GetMapping
    public List<Submission> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Submission getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // Upload - store file as Base64 in DB
    @PostMapping
    public Submission uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("studentId") Long studentId,
            @RequestParam("assignmentId") Long assignmentId) throws IOException {

        Submission s = new Submission();
        s.setStudentId(studentId);
        s.setAssignmentId(assignmentId);
        s.setFileName(file.getOriginalFilename());
        s.setFileData(Base64.getEncoder().encodeToString(file.getBytes()));

        return service.save(s);
    }

    // Re-upload - update file in DB
    @PutMapping("/{id}")
    public Submission updateFile(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        Submission s = service.getById(id);
        if (s == null) throw new RuntimeException("Submission not found");

        s.setFileName(file.getOriginalFilename());
        s.setFileData(Base64.getEncoder().encodeToString(file.getBytes()));

        return service.save(s);
    }

    // View/Download file - decode Base64 and return as file response
    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> viewFile(@PathVariable Long id) {
        Submission s = service.getById(id);
        if (s == null || s.getFileData() == null)
            return ResponseEntity.notFound().build();

        byte[] fileBytes = Base64.getDecoder().decode(s.getFileData());
        String fileName = s.getFileName() != null ? s.getFileName() : "file";

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        if (fileName.endsWith(".pdf")) mediaType = MediaType.APPLICATION_PDF;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(mediaType)
                .body(fileBytes);
    }

    // Grade
    @PutMapping("/{id}/grade")
    public Submission gradeSubmission(
            @PathVariable Long id,
            @RequestBody Submission updated) {

        Submission s = service.getById(id);
        if (s == null) throw new RuntimeException("Submission not found");

        s.setScore(updated.getScore());
        s.setFeedback(updated.getFeedback());

        return service.save(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
