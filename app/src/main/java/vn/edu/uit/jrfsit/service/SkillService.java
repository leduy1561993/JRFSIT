package vn.edu.uit.jrfsit.service;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.jrfsit.connect.Connect;
import vn.edu.uit.jrfsit.entity.Skill;

/**
 * Created by LeDuy on 11/22/2015.
 */
public class SkillService extends BaseService {
    public List<Skill> getListSkill(String userId) {
        List<Skill> listSkill;
        Connect connect = super.initConnection("doan/getListSkill.php");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userId", userId));
        JSONArray array = null;
        try {
            array = connect.getArrayObject(nameValuePairs, "listskill");
            if (array != null) {
                listSkill = new ArrayList<Skill>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject last;
                    last = new JSONObject(array.getString(i));
                    Skill skill = new Skill();
                    skill.setId(last.getString("skillId"));
                    skill.setSkill(last.getString("SkillName"));
                    skill.setExperience(last.getString("experience_year"));
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
        nameValuePairs.add(new BasicNameValuePair("userId", idUser));
        nameValuePairs.add(new BasicNameValuePair("skillId", idSkill));
        nameValuePairs.add(new BasicNameValuePair("experience_year", experienceYear));
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
        nameValuePairs.add(new BasicNameValuePair("userId", idUser));
        nameValuePairs.add(new BasicNameValuePair("skillId", idSkill));
        nameValuePairs.add(new BasicNameValuePair("experience_year", experienceYear));
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
        nameValuePairs.add(new BasicNameValuePair("userId", idUser));
        nameValuePairs.add(new BasicNameValuePair("skillId", idSkill));
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
