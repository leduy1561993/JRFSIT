// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.entity;

import java.io.Serializable;
import java.util.List;

public class ListSkill
    implements Serializable
{

    List skillList;

    public ListSkill()
    {
    }

    public List getSkillList()
    {
        return skillList;
    }

    public void setSkillList(List list)
    {
        skillList = list;
    }
}
