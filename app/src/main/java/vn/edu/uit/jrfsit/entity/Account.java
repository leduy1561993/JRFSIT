// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.entity;


public class Account
{

    private String email;
    private String imageUrl;
    private boolean isGoogle;
    private String password;
    private String userId;

    public Account()
    {
    }

    public Account(String s, String s1)
    {
        userId = s;
        password = s1;
    }

    public Account(String s, String s1, String s2, String s3, boolean flag)
    {
        userId = s;
        password = s2;
        imageUrl = s3;
        isGoogle = flag;
        email = s1;
    }

    public String getEmail()
    {
        return email;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getPassword()
    {
        return password;
    }

    public String getUserId()
    {
        return userId;
    }

    public boolean isGoogle()
    {
        return isGoogle;
    }

    public void setEmail(String s)
    {
        email = s;
    }

    public void setImageUrl(String s)
    {
        imageUrl = s;
    }

    public void setIsGoogle(boolean flag)
    {
        isGoogle = flag;
    }

    public void setPassword(String s)
    {
        password = s;
    }

    public void setUserId(String s)
    {
        userId = s;
    }
}
