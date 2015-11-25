package vn.edu.uit.jrfsit.service;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import vn.edu.uit.jrfsit.connect.Connect;

/**
 * Created by LeDuy on 11/19/2015.
 */
public class RateService extends BaseService {
    public boolean insertRate(String idUser,String idJob,String rate){
        Connect connect = super.initConnection("doan/insertRate.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idUser", idUser));
        nameValuePairs.add(new BasicNameValuePair("JobId", idJob));
        nameValuePairs.add(new BasicNameValuePair("rate", rate));
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
    public boolean updateRate(String idUser,String idJob,String rate){
        Connect connect = super.initConnection("doan/updateRate.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idUser", idUser));
        nameValuePairs.add(new BasicNameValuePair("JobId", idJob));
        nameValuePairs.add(new BasicNameValuePair("rate", rate));
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
    public float getRate(String idUser,String idJob) throws JSONException {
        Connect connect = super.initConnection("doan/getRate.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idUser", idUser));
        nameValuePairs.add(new BasicNameValuePair("JobId", idJob));
        JSONObject jsonObject = null;
        float rate;
        try {
            jsonObject = connect.getObject(nameValuePairs);
            if(jsonObject!=null&&jsonObject.getInt("success")>0){
                rate= (float) jsonObject.getDouble("rate");
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
