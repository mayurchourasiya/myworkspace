package com.sangria.profile;

import com.sangria.profile.bean.Resume;
import com.sangria.profile.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@RestController
@SpringBootApplication
public class ProfileApplication {

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostMapping(value = "/create")
	public ResponseEntity createRecord(@RequestBody Resume resume) {
		BigDecimal profileId = resumeService.createProfile(resume.getProfile());
		BigDecimal resumeId = resumeService.createResume(resume,profileId);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentContextPath().path("/retrieve/"+resumeId.intValue()).build().toUri()).build();
	}

	@GetMapping(value = "/retrieve/{resumeId}")
	public ResponseEntity getRecord(@PathVariable("resumeId") String resumeId ) {
		return ResponseEntity.ok().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProfileApplication.class, args);
	}

}
