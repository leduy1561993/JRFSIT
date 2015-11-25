package vn.edu.uit.jrfsit.service;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import vn.edu.uit.jrfsit.connect.Connect;
import vn.edu.uit.jrfsit.entity.Job;
import vn.edu.uit.jrfsit.entity.JobSearch;

/**
 * Created by LeDuy on 11/19/2015.
 */
public class JobService extends BaseService {

    public Job getJob(String JobId, String idUser) throws JSONException {

        Connect connect = super.initConnection("doan/getJobDetail.php");

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("JobId", JobId));
        nameValuePairs.add(new BasicNameValuePair("idUser", idUser));
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

                if (jsonObject.getString("rate").equals("null")) {
                    job.setRate(-1);
                } else {
                    job.setRate((float) jsonObject.getDouble("rate"));
                }
                if (jsonObject.getString("isSave").equals("null")) {
                    job.setIsSave(0);
                } else if (jsonObject.getInt("isSave") == 0) {
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


    public boolean insertSaveJob(String idUser, String idJob) {
        Connect connect = super.initConnection("doan/insertSaveJob.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idUser", idUser));
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

    public boolean updateSaveJob(String idUser, String idJob, String isSave) {
        Connect connect = super.initConnection("doan/updateSaveJob.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idUser", idUser));
        nameValuePairs.add(new BasicNameValuePair("JobId", idJob));
        nameValuePairs.add(new BasicNameValuePair("isSave", isSave));
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

    public ArrayList<JobSearch> getSaveJob(String idUser, String offset) {
        Connect connect = super.initConnection("doan/getSaveJob.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idUser", idUser));
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
}
