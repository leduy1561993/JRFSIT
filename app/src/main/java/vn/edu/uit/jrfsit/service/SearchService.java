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
 * Created by LeDuy on 11/19/2015.
 */
public class SearchService extends BaseService {
    public ArrayList<JobSearch> getResultSearch(String jobName, String location,String special, String offset,String sortMode){
        Connect connect = super.initConnection("doan/search.php");
        boolean checkKeyWord = false;
        boolean checkSortMode = false;
        boolean checkLocation = false;
        boolean checkSpecialy = false;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if(!(jobName.equals("")||jobName.equals("Mặc định"))&&!(location.equals("")||location.equals("Mặc định"))&&!(special.equals("Mặc định")|| special.equals("Chọn chuyên ngành"))){
            checkKeyWord = true;
            checkLocation = true;
            checkSpecialy = true;
        }else if(!(jobName.equals("")||jobName.equals("Mặc định"))&&!(location.equals("")||location.equals("Mặc định"))){
            checkKeyWord = true;
            checkLocation = true;
            checkSpecialy = false;
        }else if(!(jobName.equals("")||jobName.equals("Mặc định"))&&!(special.equals("Mặc định")|| special.equals("Chọn chuyên ngành"))){
            checkKeyWord = true;
            checkLocation = false;
            checkSpecialy = true;
        }else if(!location.equals("")&&!(special.equals("Mặc định")|| special.equals("Chọn chuyên ngành"))){
            checkKeyWord = false;
            checkLocation = true;
            checkSpecialy = true;
        }else if(!(jobName.equals("")||jobName.equals("Mặc định"))){
            checkKeyWord = true;
        }else if(!(location.equals("")||location.equals("Mặc định"))){
            checkLocation = true;
        }else if(!(special.equals("Mặc định")|| special.equals("Chọn chuyên ngành"))){
            checkSpecialy = true;
        }
        if(!sortMode.equals("Mặc định")){
            checkSortMode = true;
        }
        if(checkKeyWord)
            nameValuePairs.add(new BasicNameValuePair("keyWord", jobName));
        if(checkLocation)
            nameValuePairs.add(new BasicNameValuePair("location", location));
        if(checkSortMode) {
            if (sortMode.equals("Theo đánh giá")) {
                sortMode = "ratepoint";
            } else if (sortMode.equals("Theo lượt lưu")) {
                sortMode = "savepoint";
            } else {
                sortMode = "seenpoint";
            }
            nameValuePairs.add(new BasicNameValuePair("sortMode", sortMode));
        }
        if(checkSpecialy)
            nameValuePairs.add(new BasicNameValuePair("specialy",special));

        nameValuePairs.add(new BasicNameValuePair("offset", offset));

        JSONArray array= null;
        ArrayList<JobSearch> list;
        try {
            array = connect.getArrayObject(nameValuePairs, "jobsearch");
            if(array!=null){
                list = new ArrayList<JobSearch>();
                for(int i=0;i<array.length();i++){
                    JSONObject last;
                    try {
                        last = new JSONObject(array.getString(i));
                        JobSearch jobSearch= new JobSearch();
                        jobSearch.setId(last.getInt("JobId"));
                        jobSearch.setJobName(last.getString("JobName"));
                        jobSearch.setLocation(last.getString("Location"));
                        jobSearch.setCompany(last.getString("Company"));
                        jobSearch.setLogoURL(last.getString("Logo"));
                        jobSearch.setInformation(last.getString("Information"));
                        list.add(jobSearch);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                list =null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            list =null;
        } catch (JSONException e) {
            e.printStackTrace();
            list =null;
        }
        return list;
    }
}
