// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import vn.edu.uit.jrfsit.connect.Connect;

// Referenced classes of package vn.edu.uit.jrfsit.service:
//            BaseService

public class RateService extends BaseService
{
    public boolean insertRate(String userId,String idJob,String rate){
        Connect connect = super.initConnection("doan/insertRate.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", userId));
        nameValuePairs.add(new BasicNameValuePair("JobId", idJob));
        nameValuePairs.add(new BasicNameValuePair("Rate", rate));
        boolean rerult;
        try {
            rerult = connect.DUI(nameValuePairs);
        } catch (IOException e) {
            e.printStackTrace();
            rerult = false;
        } catch (JSONException e) {
            rerult = false;
            e.printStackTrace();
        }
        return rerult;
    }
    public boolean updateRate(String userId,String idJob,String rate){
        Connect connect = super.initConnection("doan/updateRate.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", userId));
        nameValuePairs.add(new BasicNameValuePair("JobId", idJob));
        nameValuePairs.add(new BasicNameValuePair("Rate", rate));
        boolean rerult;
        try {
            rerult = connect.DUI(nameValuePairs);
        } catch (IOException e) {
            e.printStackTrace();
            rerult =false;
        } catch (JSONException e) {
            e.printStackTrace();
            rerult =false;
        }

        return rerult;
    }
    public float getRate(String userId,String idJob) throws JSONException {
        Connect connect = super.initConnection("doan/getRate.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", userId));
        nameValuePairs.add(new BasicNameValuePair("JobId", idJob));
        JSONObject jsonObject = null;
        float rate;
        try {
            jsonObject = connect.getObject(nameValuePairs);
            if(jsonObject!=null&&jsonObject.getInt("success")>0){
                rate= (float) jsonObject.getDouble("Rate");
            }else {
                rate= 0;
            }
        } catch (IOException e) {
            rate=0;
            e.printStackTrace();
        }
        return rate;
    }
}
