package com.sangria.profile;

import com.sangria.profile.bean.Resume;
import com.sangria.profile.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;


@RestController
@SpringBootApplication
@CrossOrigin(allowedHeaders = "", allowCredentials = "")
public class ProfileApplication extends SpringBootServletInitializer {

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostMapping(value = "/resume")
	public ResponseEntity createRecord(@Valid @RequestBody Resume resume) {
		BigDecimal profileId = resumeService.createProfile(resume.getProfile());
		BigDecimal resumeId = resumeService.createResume(resume, profileId);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentContextPath().path("/retrieve/" + resumeId.intValue()).build().toUri()).build();
	}

	@GetMapping(value = "/resume/{resumeId}")
	public ResponseEntity getRecord(@Valid @PathVariable("resumeId") String resumeId) {
		try {
			Pattern.matches(".\\d", resumeId);
			return ResponseEntity.ok(resumeService.getResume(Integer.valueOf(resumeId)));
		} catch (SQLException e) {
			return ResponseEntity.badRequest().body("No Resume Found for given Resume ID");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Resume ID not valid");
		}
	}

	@PutMapping(value = "/resume/{resumeId}")
	public ResponseEntity updateResume(@PathVariable("resumeId") String resumeId, @RequestBody Resume resume) {
		if (null != resumeId && !resumeId.isEmpty()) {
			if (HttpStatus.OK == getRecord(resumeId).getStatusCode()) {
				resumeService.updateResume(Integer.valueOf(resumeId), resume);
				return ResponseEntity.ok("Successfully updated");
			} else {
				return ResponseEntity.badRequest().body("Resume ID not found, Hence can't be updated");
			}
		} else {
			return ResponseEntity.badRequest().body("Please try again");
		}
	}

	@DeleteMapping(value = "/resume/{resumeId}")
	public ResponseEntity deleteResume(@PathVariable("resumeId") String resumeId) {
		if (null != resumeId && !resumeId.isEmpty()) {
			if (HttpStatus.OK == getRecord(resumeId).getStatusCode()) {
				resumeService.deleteResume(Integer.valueOf(resumeId));
				return ResponseEntity.ok().body("Successfully Deleted");
			} else {
				return ResponseEntity.badRequest().body("Resume ID not found, Hence can't be deleted");
			}
		} else {
			return ResponseEntity.badRequest().body("Please try again");
		}
	}

	@GetMapping(value = "/resume")
	public ResponseEntity findAll() {
		List<Resume> resumeList = resumeService.findAll();
		return ResponseEntity.ok(resumeList);
	}

	public static void main(String[] args) throws IOException {

		SpringApplication.run(ProfileApplication.class, args);

	}

}
