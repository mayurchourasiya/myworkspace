package com.sangria.profile.bean;

import java.util.List;

public class Resume {

    private String resumeID;
    private Profile profile;
    private List<SkillSet> skillSet;
    private List<String> hobbies;

    public String getResumeID() {
        return resumeID;
    }

    public void setResumeID(String resumeID) {
        this.resumeID = resumeID;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<SkillSet> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(List<SkillSet> skillSet) {
        this.skillSet = skillSet;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }


}
