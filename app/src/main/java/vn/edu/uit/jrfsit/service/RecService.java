package vn.edu.uit.jrfsit.service;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import vn.edu.uit.jrfsit.connect.Connect;
import vn.edu.uit.jrfsit.entity.JobSearch;

/**
 * Created by LeDuy on 11/20/2015.
 */
public class RecService extends BaseService{
    public  ArrayList<JobSearch> getSaveJob(String idUser, String offset) {
        Connect connect =super.initConnection("doan/getRecJob.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idUser", idUser));
        nameValuePairs.add(new BasicNameValuePair("offset", offset));
        JSONArray array = null;
        ArrayList<JobSearch> list;
        try {
            array = connect.getArrayObject(nameValuePairs, "jobrec");
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
