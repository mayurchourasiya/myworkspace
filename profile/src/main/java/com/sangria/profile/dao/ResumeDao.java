package com.sangria.profile.dao;

import com.sangria.profile.bean.Profile;
import com.sangria.profile.bean.Resume;
import com.sangria.profile.builder.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class ResumeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    private Utility utility;

    /**
     * First create the profile and generate the profileID to required to create resume
     * @param profile
     * @return profileID
     */
    public Integer createProfile(Profile profile) {
        simpleJdbcCall = new SimpleJdbcCall(dataSource).withCatalogName("RESUME_PKG").withProcedureName("createProfile");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("i_name",profile.getName())
                .addValue("i_role",profile.getRole());
        Map<String,Object> result = simpleJdbcCall.execute(sqlParameterSource);
        Integer profileID = (Integer) result.get("o_profile_id");
        return profileID;
    }


    /**
     * Get profileID and create resume and generate resumeID
     * @param resume
     * @param profileId
     * @return resumeID
     */
    public Integer createResume(Resume resume, Integer profileId) {
        String skillSet = utility.prepareSkillSet(resume.getSkillSet());
        String hobby = utility.prepareHobbies(resume.getHobbies());
        simpleJdbcCall = new SimpleJdbcCall(dataSource).withCatalogName("RESUME_PKG").withProcedureName("createResume");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("i_profile_id",profileId)
                .addValue("i_skills",skillSet)
                .addValue("i_hobbies",hobby);
        Map<String,Object> result = simpleJdbcCall.execute(sqlParameterSource);
        Integer resumeId = (Integer) result.get("o_resume_id");
        return resumeId;
    }

}
