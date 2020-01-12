package com.sangria.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangria.profile.bean.Profile;
import com.sangria.profile.bean.Resume;
import com.sangria.profile.bean.SkillSet;
import net.minidev.json.JSONUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestApp {

    @Test
    public void create() throws Exception{
        Resume resume = new Resume();
        List<String> list = new ArrayList<>();
        resume.setHobbies(list);
        resume.getHobbies().add("tennis");
        resume.getHobbies().add("cricket");

        Profile profile =new Profile();
        profile.setName("sravan");
        profile.setRole("SE");

        SkillSet skillSet = new SkillSet();
        skillSet.setSkillName("JAVA");

        SkillSet skillSet1 = new SkillSet();
        skillSet1.setSkillName("PYTHON");

        List<SkillSet> skillSets = new ArrayList<>();
        skillSets.add(skillSet);
        skillSets.add(skillSet1);

        resume.setSkillSet(skillSets);

        resume.setProfile(profile);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(resume);
    }
}
