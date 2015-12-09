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
import vn.edu.uit.jrfsit.entity.User;

// Referenced classes of package vn.edu.uit.jrfsit.service:
//            BaseService

public class UserService extends BaseService
{
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

    public boolean insertUser(String fullName, String email, String password, String birthday, String gender, String address,
                              String phone, String careerObjective, String imageUrl)
    {
        Connect connect = super.initConnection("mail/insertUser.php");
        ArrayList arraylist = new ArrayList();
        arraylist.add(new BasicNameValuePair("fullName", fullName));
        arraylist.add(new BasicNameValuePair("email", email));
        arraylist.add(new BasicNameValuePair("password", password));
        arraylist.add(new BasicNameValuePair("birthday", birthday));
        arraylist.add(new BasicNameValuePair("gender", gender));
        arraylist.add(new BasicNameValuePair("address", address));
        arraylist.add(new BasicNameValuePair("phone", phone));
        arraylist.add(new BasicNameValuePair("careerObjective", careerObjective));
        arraylist.add(new BasicNameValuePair("imageUrl", imageUrl));
        boolean check;
        try {
            check = connect.DUI(arraylist);
        } catch (IOException e) {
            e.printStackTrace();
            check = false;
        } catch (JSONException e) {
            e.printStackTrace();
            check = false;
        }
        return check;
    }

    public String insertUserGoogle(String fullName, String email, String password, String birthday, String gender, String address,
                                   String phone, String careerObjective, String imageUrl)
    {
        Connect connect = super.initConnection("doan/insertUserGoogle.php");
        ArrayList arraylist = new ArrayList();
        arraylist.add(new BasicNameValuePair("fullName", fullName));
        arraylist.add(new BasicNameValuePair("email", email));
        arraylist.add(new BasicNameValuePair("password", password));
        arraylist.add(new BasicNameValuePair("birthday", birthday));
        arraylist.add(new BasicNameValuePair("gender", gender));
        arraylist.add(new BasicNameValuePair("address", address));
        arraylist.add(new BasicNameValuePair("phone", phone));
        arraylist.add(new BasicNameValuePair("careerObjective", careerObjective));
        arraylist.add(new BasicNameValuePair("imageUrl", imageUrl));
        String userId;
        try {
            JSONObject jsonObject = connect.getObject(arraylist);
            if (jsonObject != null && jsonObject.getInt("success") > 0) {
                userId =jsonObject.getString("UserId");
            } else {
                userId = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            userId = null;
        } catch (JSONException e) {
            e.printStackTrace();
            userId = null;
        }
        return userId;
    }

    public boolean updateCareerObjective(String userId, String careerObjective)
    {
        Connect connect = super.initConnection("doan/updateUserCareerObjective.php");
        ArrayList arraylist = new ArrayList();
        arraylist.add(new BasicNameValuePair("UserId", userId));
        arraylist.add(new BasicNameValuePair("careerObjective", careerObjective));
        boolean flag;
        try {
            flag = connect.DUI(arraylist);
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        } catch (JSONException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean updatePassword(String userId, String password)
    {
        Connect connect = super.initConnection("doan/updatePassword.php");
        ArrayList arraylist = new ArrayList();
        arraylist.add(new BasicNameValuePair("UserId", userId));
        arraylist.add(new BasicNameValuePair("Password", password));
        boolean flag;
        try {
            flag = connect.DUI(arraylist);
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        } catch (JSONException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean updateUser(String userId, String fullName, String birthday, String gender, String phone, String address)
    {
        Connect connect = super.initConnection("doan/updateUserProfile.php");
        ArrayList arraylist = new ArrayList();
        arraylist.add(new BasicNameValuePair("UserId", userId));
        arraylist.add(new BasicNameValuePair("FullName", fullName));
        arraylist.add(new BasicNameValuePair("Birthday", birthday));
        arraylist.add(new BasicNameValuePair("Gender", gender));
        arraylist.add(new BasicNameValuePair("Phone", phone));
        arraylist.add(new BasicNameValuePair("Address", address));
        boolean flag;
        try {
            flag = connect.DUI(arraylist);
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
