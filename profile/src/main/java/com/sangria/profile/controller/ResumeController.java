package com.sangria.profile.controller;

import com.sangria.profile.bean.Resume;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class ResumeController {


    @PostMapping(value = "/create")
    public ResponseEntity createRecord() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/retrieve")
    public ResponseEntity getRecord() {
        return ResponseEntity.ok().build();
    }
}
