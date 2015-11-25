package vn.edu.uit.jrfsit.service;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import vn.edu.uit.jrfsit.connect.Connect;
import vn.edu.uit.jrfsit.entity.User;

/**
 * Created by LeDuy on 11/22/2015.
 */
public class UserService extends BaseService {
    public User getUser(String userId) {
        User user;
        Connect connect = super.initConnection("doan/getUserProfile.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", userId));
        JSONObject jsonObject = null;
        try {
            jsonObject = connect.getObject(nameValuePairs);
            if (jsonObject != null && jsonObject.getInt("success") > 0) {
                user = new User();
                user.setUserId(jsonObject.getString("UserId"));
                user.setFullName(jsonObject.getString("FullName"));
                user.setEmail(jsonObject.getString("Email"));
                user.setPassword(jsonObject.getString("Password"));
                user.setBirthday(jsonObject.getString("Birthday"));
                user.setGender(jsonObject.getString("Gender"));
                user.setAddress(jsonObject.getString("Address"));
                user.setPhone(jsonObject.getString("Phone"));
                user.setCareerObjective(jsonObject.getString("Career_Objective"));
            } else {
                user = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            user = null;
        } catch (JSONException e) {
            e.printStackTrace();
            user = null;
        }
        return user;
    }
    public boolean insertUser(String idUser, String idJob) {
        Connect connect = super.initConnection("doan/insertUser.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idUser", idUser));



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
    public boolean updateUser(String userId, String fullName,String birthday,String gender, String phone,String address ) {
        Connect connect = super.initConnection("doan/updateUserProfile.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", userId));
        nameValuePairs.add(new BasicNameValuePair("FullName", fullName));
        nameValuePairs.add(new BasicNameValuePair("Birthday", birthday));
        nameValuePairs.add(new BasicNameValuePair("Gender", gender));
        nameValuePairs.add(new BasicNameValuePair("Phone", phone));
        nameValuePairs.add(new BasicNameValuePair("Address", address));
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

    public boolean updateCareerObjective(String userId, String careerObjective){
        Connect connect = super.initConnection("doan/updateUserCareerObjective.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userId", userId));
        nameValuePairs.add(new BasicNameValuePair("careerObjective", careerObjective));
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
