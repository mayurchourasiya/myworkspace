package com.sangria.profile.builder;

import com.sangria.profile.bean.SkillSet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Utility {

    public String prepareSkillSet(List<SkillSet> skillSetList)  {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;
        for (SkillSet skillSet : skillSetList) {
            counter++;
            stringBuilder.append(skillSet.getSkillName());
            if(counter < skillSetList.size()) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

    public String prepareHobbies(List<String> hobbies)  {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;
        for (String hobby : hobbies) {
            counter++;
            stringBuilder.append(hobby);
            if(counter < hobbies.size()) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

}
