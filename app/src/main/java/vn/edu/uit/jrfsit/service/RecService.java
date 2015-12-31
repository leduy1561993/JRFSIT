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
import vn.edu.uit.jrfsit.entity.JobSearch;

// Referenced classes of package vn.edu.uit.jrfsit.service:
//            BaseService

public class RecService extends BaseService
{
    public  ArrayList<JobSearch> getRecJob(String userId, String offset) {
        Connect connect =super.initConnection("doan/getRecJob.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", userId));
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
    public int getRecNotification(String userId)  {
        Connect connect = super.initConnection("doan/getRecNotifi.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", userId));
        JSONObject jsonObject = null;
        int rate;
        try {
            jsonObject = connect.getObject(nameValuePairs);
            if(jsonObject!=null&&jsonObject.getInt("success")>0){
                rate= jsonObject.getInt("recNoti");
            }else {
                rate= 0;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
            rate= 0;
        } catch (IOException e) {
            rate=0;
            e.printStackTrace();
        }
        return rate;
    }
    public boolean updateLocationRec(String userId, String location)  {
        Connect connect = super.initConnection("doan/updateLocation.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", userId));
        nameValuePairs.add(new BasicNameValuePair("Location", location));
        boolean flag;
        try {
            flag = connect.DUI(nameValuePairs);
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        } catch (JSONException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}
