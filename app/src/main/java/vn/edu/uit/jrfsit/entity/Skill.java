package vn.edu.uit.jrfsit.entity;

import java.io.Serializable;

/**
 * Created by LeDuy on 11/1/2015.
 */
public class Skill implements Serializable {
    public Skill(String id,String skill, String experience) {
        this.skill = skill;
        this.id =id;
        this.experience = experience;
    }

    public Skill(){

    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    private String skill;
    private String experience;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
}
