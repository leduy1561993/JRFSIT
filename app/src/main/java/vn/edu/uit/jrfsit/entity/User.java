// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.entity;

import java.io.Serializable;

public class User
    implements Serializable
{

    private String address;
    private String birthday;
    private String careerObjective;
    private String email;
    private String fullName;
    private String gender;
    private String imageUrl;
    private String password;
    private String phone;
    private String userId;

    public User()
    {
    }

    public String getAddress()
    {
        return address;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public String getCareerObjective()
    {
        return careerObjective;
    }

    public String getEmail()
    {
        return email;
    }

    public String getFullName()
    {
        return fullName;
    }

    public String getGender()
    {
        return gender;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getPassword()
    {
        return password;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setAddress(String s)
    {
        address = s;
    }

    public void setBirthday(String s)
    {
        birthday = s;
    }

    public void setCareerObjective(String s)
    {
        careerObjective = s;
    }

    public void setEmail(String s)
    {
        email = s;
    }

    public void setFullName(String s)
    {
        fullName = s;
    }

    public void setGender(String s)
    {
        gender = s;
    }

    public void setImageUrl(String s)
    {
        imageUrl = s;
    }

    public void setPassword(String s)
    {
        password = s;
    }

    public void setPhone(String s)
    {
        phone = s;
    }

    public void setUserId(String s)
    {
        userId = s;
    }
}
