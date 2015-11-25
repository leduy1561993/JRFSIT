package vn.edu.uit.jrfsit.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LeDuy on 11/21/2015.
 */
public class ListSkill implements Serializable {
    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    List <Skill> skillList;
}
