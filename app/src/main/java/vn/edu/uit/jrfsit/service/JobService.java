// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vn.edu.uit.jrfsit.connect.Connect;
import vn.edu.uit.jrfsit.entity.Job;
import vn.edu.uit.jrfsit.entity.JobSearch;

// Referenced classes of package vn.edu.uit.jrfsit.service:
//            BaseService

public class JobService extends BaseService
{
    public Job getJob(String JobId, String userId) throws JSONException {

        Connect connect = super.initConnection("doan/getJobDetail.php");

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("JobId", JobId));
        nameValuePairs.add(new BasicNameValuePair("UserId", userId));
        JSONObject jsonObject = null;
        Job job;
        try {
            jsonObject = connect.getObject(nameValuePairs);
            if (jsonObject != null && jsonObject.getInt("success") > 0) {
                job = new Job();
                job.setJobId(jsonObject.getString("JobId"));
                job.setJobName(jsonObject.getString("JobName"));
                job.setLocation(jsonObject.getString("Location"));
                job.setSalary(jsonObject.getString("Salary"));
                job.setDescription(jsonObject.getString("Description"));
                job.setRequirement(jsonObject.getString("Requirement"));
                job.setBenifit(jsonObject.getString("Benifit"));
                job.setExpired(jsonObject.getString("Expired"));
                job.setSource(jsonObject.getString("Source"));
                job.setCompany(jsonObject.getString("Company"));
                job.setLogo(jsonObject.getString("Logo"));

                if (jsonObject.getString("Rate").equals("null")) {
                    job.setRate(-1);
                } else {
                    job.setRate((float) jsonObject.getDouble("Rate"));
                }
                if (jsonObject.getString("IsSave").equals("null")) {
                    job.setIsSave(0);
                } else if (jsonObject.getInt("IsSave") == 0) {
                    job.setIsSave(1);
                } else {
                    job.setIsSave(2);
                }

            } else {
                job = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            job = null;
        }
        return job;
    }

    public ArrayList<JobSearch> getSaveJob(String UserId, String offset) {
        Connect connect = super.initConnection("doan/getSaveJob.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", UserId));
        nameValuePairs.add(new BasicNameValuePair("offset", offset));
        JSONArray array = null;
        ArrayList<JobSearch> list;
        try {
            array = connect.getArrayObject(nameValuePairs, "jobsave");
            if (array != null) {
                list = new ArrayList<JobSearch>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject last;
                    last = new JSONObject(array.getString(i));
                    JobSearch jobSearch = new JobSearch();
                    jobSearch.setId(last.getInt("JobId"));
                    jobSearch.setJobName(last.getString("JobName"));
                    jobSearch.setLocation(last.getString("Location"));
                    jobSearch.setCompany(last.getString("Company"));
                    jobSearch.setLogoURL(last.getString("Logo"));
                    jobSearch.setInformation(last.getString("Information"));
                    list.add(jobSearch);
                }
            } else {
                list = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            list = null;
        } catch (JSONException e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

    public boolean updateSaveJob(String UserId, String idJob, String isSave) {
        Connect connect = super.initConnection("doan/updateSaveJob.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", UserId));
        nameValuePairs.add(new BasicNameValuePair("JobId", idJob));
        nameValuePairs.add(new BasicNameValuePair("IsSave", isSave));
        String check;
        boolean result;
        try {
            result = connect.DUI(nameValuePairs);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } catch (JSONException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    public boolean insertSaveJob(String UserId, String idJob) {
        Connect connect = super.initConnection("doan/insertSaveJob.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", UserId));
        nameValuePairs.add(new BasicNameValuePair("JobId", idJob));
        boolean result;
        String check;
        try {
            result = connect.DUI(nameValuePairs);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } catch (JSONException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }
}
