package com.sangria.profile.controller;

import com.sangria.profile.bean.Resume;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
public class ResumeController {


    @PostMapping(value = "/create")
    public ResponseEntity createRecord(@RequestBody Resume resume) {

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/retrieve")
    public ResponseEntity getRecord() {
        return ResponseEntity.ok().build();
    }
}
