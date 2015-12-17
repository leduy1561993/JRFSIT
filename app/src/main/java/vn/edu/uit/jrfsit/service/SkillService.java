// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vn.edu.uit.jrfsit.connect.Connect;
import vn.edu.uit.jrfsit.entity.Skill;

// Referenced classes of package vn.edu.uit.jrfsit.service:
//            BaseService

public class SkillService extends BaseService
{
    public List<Skill> getListSkill(String userId) {
        List<Skill> listSkill;
        Connect connect = super.initConnection("doan/getListSkill.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", userId));
        JSONArray array = null;
        try {
            array = connect.getArrayObject(nameValuePairs, "listskill");
            if (array != null) {
                listSkill = new ArrayList<Skill>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject last;
                    last = new JSONObject(array.getString(i));
                    Skill skill = new Skill();
                    skill.setId(last.getString("SkillId"));
                    skill.setSkill(last.getString("SkillName"));
                    skill.setExperience(last.getString("Experience_Year"));
                    listSkill.add(skill);
                }
            } else {
                listSkill = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            listSkill = null;
        } catch (JSONException e) {
            e.printStackTrace();
            listSkill = null;
        }
        return listSkill;
    }
    public boolean insertSkill(String idUser, String idSkill, String experienceYear) {
        Connect connect = super.initConnection("doan/insertSkill.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", idUser));
        nameValuePairs.add(new BasicNameValuePair("SkillId", idSkill));
        nameValuePairs.add(new BasicNameValuePair("Experience_Year", experienceYear));
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
    public boolean updateSkill(String idUser, String idSkill, String experienceYear) {
        Connect connect = super.initConnection("doan/updateSkill.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", idUser));
        nameValuePairs.add(new BasicNameValuePair("SkillId", idSkill));
        nameValuePairs.add(new BasicNameValuePair("Experience_Year", experienceYear));
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
    public boolean deleteSkill(String idUser, String idSkill) {
        Connect connect = super.initConnection("doan/deleteSkill.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserId", idUser));
        nameValuePairs.add(new BasicNameValuePair("SkillId", idSkill));
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
}
