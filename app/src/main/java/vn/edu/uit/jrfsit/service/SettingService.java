// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.service;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vn.edu.uit.jrfsit.connect.Connect;
import vn.edu.uit.jrfsit.entity.Setting;
import vn.edu.uit.jrfsit.entity.Skill;

// Referenced classes of package vn.edu.uit.jrfsit.service:
//            BaseService

public class SettingService extends BaseService
{
    public Setting getSetting(String userId)
    {
        Connect connect = super.initConnection("doan/getSetting.php");
        ArrayList arraylist = new ArrayList();
        arraylist.add(new BasicNameValuePair("UserId", userId));
        Setting setting;
        try {
            JSONObject jsonObject = connect.getObject(arraylist);
            if (jsonObject != null && jsonObject.getInt("success") > 0) {
                setting = new Setting();
                setting.setLocation(jsonObject.getString("Location"));
                setting.setNumberRec(jsonObject.getString("NumberRec"));
                setting.setSkillID(jsonObject.getString("SkillId"));
                setting.setSkill(jsonObject.getString("Skill"));
                setting.setTimeRec(jsonObject.getString("TimeRec"));
                setting.setState(jsonObject.getString("State"));
            } else {
                setting = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            setting = null;
        } catch (JSONException e) {
            e.printStackTrace();
            setting = null;
        }
        return setting;
    }

    public boolean updateSetting(String s, String s1, String s2, String s3, String s4, String s5)
    {
        Connect connect = super.initConnection("doan/updateSettingRec.php");
        ArrayList arraylist = new ArrayList();
        arraylist.add(new BasicNameValuePair("UserId", s));
        arraylist.add(new BasicNameValuePair("Location", s1));
        arraylist.add(new BasicNameValuePair("NumberRec", s2));
        arraylist.add(new BasicNameValuePair("SkillID", s3));
        arraylist.add(new BasicNameValuePair("TimeRec", s4));
        arraylist.add(new BasicNameValuePair("State", s5));
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
