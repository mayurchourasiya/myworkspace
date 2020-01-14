package com.sangria.profile.service;

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
import java.util.Map;

@Component
public class ResumeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    private Utility utility;

    /**
     * First create the profile and generate the profileID to required to create resume
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

}
