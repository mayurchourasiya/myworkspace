package com.sangria.profile.controller;

import com.sangria.profile.bean.Resume;
import com.sangria.profile.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping(value = "/create")
    public ResponseEntity createRecord(@RequestBody Resume resume) {
        Integer resumeId = resumeService.createResume(resume);
        return ResponseEntity.ok(resumeId);
    }

    @GetMapping(value = "/retrieve")
    public ResponseEntity getRecord() {
        return ResponseEntity.ok().build();
    }
}
