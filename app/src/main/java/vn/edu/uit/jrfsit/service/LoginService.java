// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.service;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import vn.edu.uit.jrfsit.connect.Connect;
import vn.edu.uit.jrfsit.entity.Account;

// Referenced classes of package vn.edu.uit.jrfsit.service:
//            BaseService

public class LoginService extends BaseService
{
    public Account LoginUser(String email, String password)
    {

        Connect connect = super.initConnection("doan/login.php");
        ArrayList arraylist = new ArrayList();
        arraylist.add(new BasicNameValuePair("Email", email));
        arraylist.add(new BasicNameValuePair("Password", password));

        Account account;
        JSONObject jsonObject = null;
        try
        {
            jsonObject = connect.getObject(arraylist);
            if (jsonObject != null && jsonObject.getInt("success") > 0) {
                account = new Account();
                account.setUserId(jsonObject.getString("UserId"));
                account.setPassword(jsonObject.getString("Password"));
                account.setImageUrl(jsonObject.getString("imageUrl"));
                account.setIsGoogle(false);
            } else {
                account = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            account = null;
        } catch (IOException e) {
            e.printStackTrace();
            account = null;
        }
        return account;
    }
}
