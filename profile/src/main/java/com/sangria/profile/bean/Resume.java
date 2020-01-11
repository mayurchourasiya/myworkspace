package com.sangria.profile.bean;

import java.util.List;

public class Resume {

    private Profile profile;
    private List<SkillSet> skillSet;

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

    private List<String> hobbies;
}
