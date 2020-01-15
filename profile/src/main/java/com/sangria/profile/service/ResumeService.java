package com.sangria.profile.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangria.profile.bean.Profile;
import com.sangria.profile.bean.Resume;
import com.sangria.profile.builder.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ResumeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    private Utility utility;

    /**
     * First create the profile and generate the profileID required to create resume
     * @param profile
     * @return profileID
     */
    public BigDecimal createProfile(Profile profile) {
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withCatalogName("RESUME_PKG").withProcedureName("createProfile");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("i_name",profile.getName())
                .addValue("i_role",profile.getRole());
        Map<String,Object> result = simpleJdbcCall.execute(sqlParameterSource);
        return (BigDecimal) result.get("O_PROFILE_ID");
    }


    /**
     * Get profileID and create resume and generate resumeID
     * @param resume
     * @param profileId
     * @return resumeID
     */
    public BigDecimal createResume(Resume resume, BigDecimal profileId) {
        String skillSet = utility.prepareSkillSet(resume.getSkillSet());
        String hobby = utility.prepareHobbies(resume.getHobbies());
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withCatalogName("RESUME_PKG").withProcedureName("createResume");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("i_profile_id",profileId)
                .addValue("i_skills",skillSet)
                .addValue("i_hobbies",hobby);
        Map<String,Object> result = simpleJdbcCall.execute(sqlParameterSource);
        return (BigDecimal) result.get("O_RESUME_ID");
    }

    /**
     * To retrieve the resume by providing resumeID
     * @param resumeId
     * @return Resume
     */
    public Resume getResume(Integer resumeId){
        Map<String,Object> resumeResult = callProcedure("getResume","i_resume_id",resumeId);
        String skillSet = (String) resumeResult.get("O_SKILLS");
        String hobby = (String) resumeResult.get("O_HOBBIES");
        BigDecimal profileId = (BigDecimal) resumeResult.get("O_PROFILE_ID");

        Map<String,Object> profileResult = callProcedure("getProfile","i_profile_id",profileId.intValue());
        String name = (String) profileResult.get("O_NAME");
        String role = (String) profileResult.get("O_ROLE");

        Profile profile = new Profile();
        profile.setName(name);
        profile.setRole(role);

        Resume resume = new Resume();
        resume.setProfile(profile);
        return utility.prepareResume(skillSet.split(","),hobby.split(","),resume);
    }

    /**
     * To update the resume
     * @param resumeID
     * @return
     */
    public BigDecimal updateResume(Integer resumeID, Resume resume) {

        String skillSet = utility.prepareSkillSet(resume.getSkillSet());
        String hobby = utility.prepareHobbies(resume.getHobbies());
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withCatalogName("RESUME_PKG").withProcedureName("updateResume");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("i_resume_id",resumeID).addValue("i_skills",skillSet)
                .addValue("i_hobbies",hobby).addValue("i_name",resume.getProfile().getName()).addValue("i_role",resume.getProfile().getRole());
        Map<String,Object> result = simpleJdbcCall.execute(sqlParameterSource);
        return (BigDecimal) result.get("O_RESUME_ID");
    }


    public void deleteResume(Integer resumeID) {
        String RESUME_SQL = "delete from RESUME_RECORDS where resume_id = ?";
        String PROFILE_SQL = "delete from PROFILES where profile_id = ?";
        String profileID = jdbcTemplate.queryForObject("select PROFILE_ID from RESUME_RECORDS where resume_id = ?",new Object[]{resumeID}, String.class);

        jdbcTemplate.update(RESUME_SQL,resumeID);
        jdbcTemplate.update(PROFILE_SQL,profileID);
    }

    public List<Resume> findAll() {
        List<Resume> resumeList = new ArrayList<>();
        List<Map<String, Object>> recordsList = jdbcTemplate.queryForList("SELECT resume_records.resume_id, profiles.name FROM resume_records INNER JOIN profiles ON resume_records.resume_id = profiles.profile_id ORDER BY resume_id");
        for(Map m : recordsList) {
            Resume resume = new Resume();
            resume.setResumeID(String.valueOf(m.get("resume_id")));
            Profile profile = new Profile();
            profile.setName(String.valueOf(m.get("name")));
            resume.setProfile(profile);;
            resumeList.add(resume);
        }
        return resumeList;
    }


    /**
     * Common method to call procedure
     * @param procedure
     * @param input
     * @param id
     * @return Map<String,Object>
     */
    private Map<String,Object> callProcedure(String procedure,String input,Integer id){
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withCatalogName("RESUME_PKG").withProcedureName(procedure);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue(input,id);
        return simpleJdbcCall.execute(sqlParameterSource);
    }

}
