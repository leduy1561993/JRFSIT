// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.entity;

import java.io.Serializable;

public class Skill
    implements Serializable
{

    private String experience;
    private String id;
    private String skill;

    public Skill()
    {
    }

    public Skill(String s, String s1, String s2)
    {
        skill = s1;
        id = s;
        experience = s2;
    }

    public String getExperience()
    {
        return experience;
    }

    public String getId()
    {
        return id;
    }

    public String getSkill()
    {
        return skill;
    }

    public void setExperience(String s)
    {
        experience = s;
    }

    public void setId(String s)
    {
        id = s;
    }

    public void setSkill(String s)
    {
        skill = s;
    }
}
