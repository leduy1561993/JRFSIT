// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.entity;


public class JobSearch
{

    private String company;
    private long dateTime;
    int id;
    private String information;
    private String jobName;
    private String location;
    private String logoURL;
    private String salary;
    private String sortmode;
    private String specialy;

    public JobSearch()
    {
        dateTime = 0L;
    }

    public JobSearch(int i, String s, String s1, String s2, String s3)
    {
        id = i;
        jobName = s;
        logoURL = s3;
        location = s1;
        specialy = s2;
    }

    public JobSearch(int i, String s, String s1, String s2, String s3, long l)
    {
        id = i;
        jobName = s;
        location = s1;
        sortmode = s3;
        specialy = s2;
        dateTime = l;
    }

    public JobSearch(String s, String s1, String s2, String s3, long l)
    {
        jobName = s;
        location = s1;
        sortmode = s2;
        specialy = s3;
        dateTime = l;
    }

    public String getCompany()
    {
        return company;
    }

    public long getDateTime()
    {
        return dateTime;
    }

    public int getId()
    {
        return id;
    }

    public String getInformation()
    {
        return information;
    }

    public String getJobName()
    {
        return jobName;
    }

    public String getLocation()
    {
        return location;
    }

    public String getLogoURL()
    {
        return logoURL;
    }

    public String getSalary()
    {
        return salary;
    }

    public String getSortmode()
    {
        return sortmode;
    }

    public String getSpecialy()
    {
        return specialy;
    }

    public void setCompany(String s)
    {
        company = s;
    }

    public void setDateTime(int i)
    {
        dateTime = i;
    }

    public void setId(int i)
    {
        id = i;
    }

    public void setInformation(String s)
    {
        information = s;
    }

    public void setJobName(String s)
    {
        jobName = s;
    }

    public void setLocation(String s)
    {
        location = s;
    }

    public void setLogoURL(String s)
    {
        logoURL = s;
    }

    public void setSalary(String s)
    {
        salary = s;
    }

    public void setSortmode(String s)
    {
        sortmode = s;
    }

    public void setSpecialy(String s)
    {
        specialy = s;
    }
}
